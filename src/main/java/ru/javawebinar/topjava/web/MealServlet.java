package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.DataMealInListImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    MealDao dao = DataMealInListImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(),
                LocalTime.MIN,
                LocalTime.MAX,
                2000);
        request.setAttribute("meals", mealWithExceeds);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
