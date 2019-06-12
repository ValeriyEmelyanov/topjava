package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.Model;
import ru.javawebinar.topjava.model.ModelList;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@WebServlet("meals.jsp")
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Model<Meal> MODEL = ModelList.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");

        List<Meal> meals = MODEL.list();
        List<MealTo> mealTos = MealsUtil.getFilteredWithExcess(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("dateTimeFormater", dateTimeFormater);
        request.setAttribute("mealTos", mealTos);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug("action: " + action);

        if (action == null) {
            doGet(request, response);
            return;
        }

        switch (action) {
            case "forward":
                String page = request.getParameter("page");
                request.getRequestDispatcher(page).forward(request, response);
                return;
            case "add":
                Meal mealToAdd = newMealFromRequest(request);
                MODEL.add(mealToAdd);
                request.setAttribute("addResult", mealToAdd.toString());
                request.getRequestDispatcher("mealedit.jsp").forward(request, response);
                return;
            case "forwardToEdit": {
                int id = Integer.valueOf(request.getParameter("id"));
                Meal meal = MODEL.getById(id);
                if (meal == null) {
                    log.warn("not found id:" + id);
                    doGet(request, response);
                    return;
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealedit.jsp").forward(request, response);
                return;
            }
            case "update": {
                int id = Integer.valueOf(request.getParameter("id"));
                Meal meal = MODEL.getById(id);
                if (meal == null) {
                    log.warn("not found id:" + id);
                    doGet(request, response);
                    return;
                }
                updateMealFromRequest(request, meal);
                MODEL.update(meal);
                doGet(request, response);
                return;
            }
            case "delete":
                int id = Integer.valueOf(request.getParameter("id"));
                MODEL.delete(id);
                doGet(request, response);
                return;
        }
    }

    private Meal newMealFromRequest(HttpServletRequest request) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        return new Meal(LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
    }

    private void updateMealFromRequest(HttpServletRequest request, Meal meal) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        meal.setDateTime(LocalDateTime.parse(dateTime));
        meal.setDescription(description);
        meal.setCalories(Integer.valueOf(calories));
    }
}
