package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DataMealInListImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_USER = "meals";

    private static final Logger LOG = getLogger(MealServlet.class);

    private MealDao dao = DataMealInListImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String id = request.getParameter("id");
            LocalDateTime date = LocalDateTime.parse(request.getParameter("date"), TimeUtil.format);
            String desc = request.getParameter("desc");
            Integer cal = Integer.parseInt(request.getParameter("cal"));

            if (id == null || id.isEmpty()) {
                LOG.debug("added new meal");
                dao.addMeal(new Meal(date, desc, cal));
            } else {
                LOG.debug("Update meal with id = " + id);
                dao.updateMeal(new Meal(Integer.parseInt(id), date, desc, cal));
            }
        } catch (Exception e) {
            LOG.debug("Error parse parameters");
        }

        response.sendRedirect(LIST_USER);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        //NPE protection
        action = action != null ? action : "userList";

        switch (action) {
            case "edit": {
                int id = Integer.parseInt(request.getParameter("Id"));
                request.setAttribute("meal", dao.getMealById(id));
            }
            case "insert": {
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                return;
            }
            case "delete": {
                int id = Integer.parseInt(request.getParameter("Id"));
                LOG.debug("Delete meal with id = " + id);
                dao.deleteMeal(id);
                response.sendRedirect(LIST_USER);
                return;
            }
            default: {
                List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        2000);
                request.setAttribute("meals", mealWithExceeds);
            }
        }

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
