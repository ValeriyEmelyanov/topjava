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
import java.util.Objects;

//@WebServlet("meals.jsp")
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Model<Meal> model = ModelList.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        log.debug("Action: {}", action);

        switch (action == null ? "list" : action) {
            case "delete":
                int id = getId(request);
                log.debug("Delete id: {}", id);
                model.delete(id);
                response.sendRedirect("meals");
                return;
            case "add":
                request.setAttribute("meal", null);
                request.getRequestDispatcher("/mealform.jsp").forward(request, response);
                return;
            case "edit":
                request.setAttribute("meal", model.getById(getId(request)));
                request.getRequestDispatcher("/mealform.jsp").forward(request, response);
                return;
            case "list":
            default:
                request.setAttribute("dateTimeFormater", DATE_TIME_FORMATTER);
                request.setAttribute("mealTos", model.list());
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            // Add
            Meal newMeal = newMealFromRequest(request);
            model.add(newMeal);
            log.debug("Added new meal: {}", newMeal);
        } else {
            // Update
            Meal meal = model.getById(Integer.parseInt(id));
            if (meal == null) {
                log.warn("Not found id: " + id);
            } else {
                updateMealFromRequest(request, meal);
                model.update(meal);
                log.debug("Updated meal: {}", meal);
            }
        }
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
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
