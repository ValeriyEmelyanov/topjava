package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void create(Meal meal) {
        int userId = authUserId();
        log.info("create {} for user {}", meal, userId);
        meal.setUserId(userId);
        service.create(meal, authUserId());
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void update(Meal meal) {
        int userId = authUserId();
        log.info("update {} for user {}", meal.getId(), userId);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealTo> list() {
        int userId = authUserId();
        log.info("list for user {}", userId);
        return service.list(userId);
    }

    public List<MealTo> listBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = authUserId();
        log.info("list between dates {} - {} and time {} - {} for user {}",
                startDate, endDate, startTime, endTime, userId);

        return service.listBetweenDates(startDate, endDate, userId).stream()
                .filter(mealTo -> Util.isBetween(mealTo.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}