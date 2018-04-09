package cz.muni.fi.pv168.web;


import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class StartListener implements ServletContextListener {

    final static Logger log = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        log.info("aplikace inicializována");
        ServletContext servletContext = ev.getServletContext();
        ApplicationContext springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        servletContext.setAttribute("agentManager", springContext.getBean("agentManager", AgentManagerImpl.class));
        servletContext.setAttribute("missionManager", springContext.getBean("missionManager", MissionManagerImpl.class));
        servletContext.setAttribute("contractManager", springContext.getBean("contractManager", ContractManagerImpl.class));
        log.info("vytvořeny manažery");
    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        log.info("aplikace končí");
    }
}