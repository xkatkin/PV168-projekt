package cz.muni.fi.pv168.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        System.out.println("aplikace inicializována");
        ServletContext servletContext = ev.getServletContext();
        String serverInfo = servletContext.getServerInfo();
        servletContext.log("serevrInfo = " + serverInfo);

        servletContext.setAttribute("key", "value");
    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        System.out.println("aplikace končí");
    }
}