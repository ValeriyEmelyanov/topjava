package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private final MealService service;

    @Autowired
    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals",
                MealsUtil.getWithExcess(service.getAll(
                        SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));

        return "meals";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        service.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable String id, Model model) {
        Meal meal = service.get(Integer.parseInt(id), SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @PostMapping()
    public String createAdnUpdate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String strId = request.getParameter("id");
        if (StringUtils.isEmpty(strId)) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.parseInt(strId));
            service.update(meal, SecurityUtil.authUserId());
        }

        return "redirect:/meals";
    }

    @GetMapping("filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsFiltered = service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> mealsToFiltered = MealsUtil.getFilteredWithExcess(
                mealsFiltered, SecurityUtil.authUserId(), startTime, endTime);

        model.addAttribute("meals", mealsToFiltered);

        return "meals";
    }
}
