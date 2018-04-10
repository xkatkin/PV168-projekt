
package cz.muni.fi.pv168.web;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.agents.Equipment;
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
@WebServlet(AgentsServlet.URL_MAPPING + "/*")
public class AgentsServlet extends HttpServlet {

    private static final String LIST_JSP = "/agentList.jsp";
    public static final String URL_MAPPING = "/agents";

    private final static Logger log = LoggerFactory.getLogger(AgentServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAgentsList(request, response);
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
                String fullName = request.getParameter("fullName");
                String secretName = request.getParameter("secretName");
                String equipment = request.getParameter("equipment");
                //kontrola vyplnění hodnot
                if (fullName == null || fullName.length() == 0 || secretName == null || secretName.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    showAgentsList(request, response);
                    return;
                }
                //zpracování dat - vytvoření záznamu v databázi
                try {
                    Agent agent = new Agent(null, fullName, secretName, Equipment.valueOf(equipment))
                    getAgentManager().createAgent(agent);
                    log.debug("created {}",agent);
                    //redirect-after-POST je ochrana před vícenásobným odesláním formuláře
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalArgumentException e) {
                    log.error("Cannot add agent", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getAgentManager().deleteAgent(id);
                    log.debug("deleted book {}",id);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalArgumentException e) {
                    log.error("Cannot delete agent", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
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
    private AgentManagerImpl getAgentManager() {
        return (AgentManagerImpl) getServletContext().getAttribute("agentManager");
    }

    /**
     * Stores the list of books to request attribute "books" and forwards to the JSP to display it.
     */
    private void showAgentsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("agents", getAgentManager().findAllAgents());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (IllegalArgumentException e) {
            log.error("Cannot show agents", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}

}