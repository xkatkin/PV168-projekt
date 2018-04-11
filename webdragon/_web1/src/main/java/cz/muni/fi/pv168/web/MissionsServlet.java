package cz.muni.fi.pv168.web;


import cz.muni.fi.agents.Equipment;
import cz.muni.fi.missions.MissionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.missions.Mission;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet for managing books.
 */
@WebServlet(MissionsServlet.URL_MAPPING + "/*")
public class MissionsServlet extends HttpServlet {

    private static final String LIST_JSP = "/missionList.jsp";
    public static final String URL_MAPPING = "/missions";

    private final static Logger log = LoggerFactory.getLogger(MissionsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showMissionsList(request, response);
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
                String target = request.getParameter("target");
                String equipment = request.getParameter("necessaryEquipment");
                String day = request.getParameter("day");
                String month = request.getParameter("month");
                String year = request.getParameter("year");

                //kontrola vyplnění hodnot
                if (target == null || target.length() == 0 ||
                        equipment == null || equipment.length() == 0 ||
                        day == null || day.length() == 0 ||
                        month == null || month.length() == 0 ||
                        year == null || year.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    showMissionsList(request, response);
                    return;
                }

                //equipment validation
                Equipment equip;
                try {
                    equip= Equipment.valueOf(equipment.toUpperCase());
                } catch (IllegalArgumentException e){
                    request.setAttribute("chyba", "Enter valid equipment! (KNIFE, GUN, MOJITO, SNIPERRIFLE, BADASSCAR, CHARMINGCOMPANION)");
                    showMissionsList(request, response);
                    return;
                }

                //date validation
                LocalDate date;
                try {
                    date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                } catch (java.time.DateTimeException e){
                    request.setAttribute("chyba", "Enter valid date!");
                    showMissionsList(request, response);
                    return;
                }

                //zpracování dat - vytvoření záznamu v databázi
                try {
                    Mission mission = new Mission(0L, target, equip, date);
                    getMissionManager().createMission(mission);
                    log.debug("created {}",mission);
                    //redirect-after-POST je ochrana před vícenásobným odesláním formuláře
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalArgumentException e) {
                    log.error("Cannot add mission", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getMissionManager().deleteMission(id);
                    log.debug("deleted mission {}",id);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalArgumentException e) {
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
    private MissionManager getMissionManager() {
        return (MissionManager) getServletContext().getAttribute("missionManager");
    }

    /**
     * Stores the list of books to request attribute "books" and forwards to the JSP to display it.
     */
    private void showMissionsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("missions", getMissionManager().findAllMissions());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (IllegalArgumentException e) {
            log.error("Cannot show Missions", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}