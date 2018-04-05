
package cz.muni.fi.pv168.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(urlPatterns = {"/muj/*", "*.muj"})
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Muj prvni servlet</h1>");

        int n = 10;
        String np = request.getParameter("n");
        if (np != null) n = Integer.parseInt(np);

        n *= 2;
        for (int i = 0; i < n; i++) {
            out.println("i=" + i + "<br/>");
        }

        Enumeration<String> headernames = request.getHeaderNames();
        while (headernames.hasMoreElements()){
            String header = headernames.nextElement();

        }
    }

}