package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ModelList implements Model<Meal> {
    private static ModelList instance = new ModelList();
    private Map<Integer, Meal> meals;

    public static ModelList getInstance() {
        return instance;
    }

    private ModelList() {
        meals = new ConcurrentHashMap<>();
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> list() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void add(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        // Nothing to do
        //meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
}
