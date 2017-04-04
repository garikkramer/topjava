package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static final ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

    private MealRestController controller = appCtx.getBean(MealRestController.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action){
            case "save":{
                String id = request.getParameter("id");

                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        AuthorizedUser.id(),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));

                LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                controller.save(meal);
                break;
            }
            case "sorted":{
                String sorter = request.getParameter("sorter");
                controller.setSorter(MealRestController.Sorter.valueOf(sorter));
                break;
            }
            case "filtering":{
                controller.setStartDate(DateTimeUtil.getDate(request.getParameter("startDate")));
                controller.setEndDate(DateTimeUtil.getDate(request.getParameter("endDate")));
                controller.setStartTime(DateTimeUtil.getTime(request.getParameter("startTime")));
                controller.setEndTime(DateTimeUtil.getTime(request.getParameter("endTime")));
            }
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("sorter", controller.getSorter().name());

                request.setAttribute("startDate", controller.getStartDate());
                request.setAttribute("endDate", controller.getEndDate());
                request.setAttribute("startTime",controller.getStartTime());
                request.setAttribute("endTime", controller.getEndTime());

                request.setAttribute("meals", controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}