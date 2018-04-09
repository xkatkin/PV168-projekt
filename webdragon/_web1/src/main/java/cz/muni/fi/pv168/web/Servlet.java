
package cz.muni.fi.pv168.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


/**
 * Servlet for managing books.
 */
@WebServlet(BooksServlet.URL_MAPPING + "/*")
public class BooksServlet extends HttpServlet {

    private static final String LIST_JSP = "/missionList.jsp";
    public static final String URL_MAPPING = "/books";

    private final static Logger log = LoggerFactory.getLogger(BooksServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showBooksList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //aby fungovala čestina z formuláře
        request.setCharacterEncoding("utf-8");
        //akce podle přípony v URL
        String action = request.getPathInfo();
        switch (action) {
            case "/add":
                //načtení POST parametrů z formuláře
                String name = request.getParameter("name");
                String author = request.getParameter("author");
                //kontrola vyplnění hodnot
                if (name == null || name.length() == 0 || author == null || author.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    showBooksList(request, response);
                    return;
                }
                //zpracování dat - vytvoření záznamu v databázi
                try {
                    Book book = new Book(null, name, author);
                    getBookManager().createBook(book);
                    log.debug("created {}",book);
                    //redirect-after-POST je ochrana před vícenásobným odesláním formuláře
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (BookException e) {
                    log.error("Cannot add book", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getBookManager().deleteBook(id);
                    log.debug("deleted book {}",id);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (BookException e) {
                    log.error("Cannot delete book", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                //TODO
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    /**
     * Gets BookManager from ServletContext, where it was stored by {@link StartListener}.
     *
     * @return BookManager instance
     */
    private BookManager getBookManager() {
        return (BookManager) getServletContext().getAttribute("bookManager");
    }

    /**
     * Stores the list of books to request attribute "books" and forwards to the JSP to display it.
     */
    private void showBooksList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("books", getBookManager().getAllBooks());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (BookException e) {
            log.error("Cannot show books", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}

}