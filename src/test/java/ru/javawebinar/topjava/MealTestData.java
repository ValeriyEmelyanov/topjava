package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int ID_1 = 1;
    public static final int ID_2 = ID_1 + 1;
    public static final Meal MEAL1 = new Meal(ID_1,     LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(ID_1 + 1, LocalDateTime.of(2019, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(ID_1 + 2, LocalDateTime.of(2019, Month.MAY, 30, 18, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(ID_1 + 3, LocalDateTime.of(2019, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(ID_1 + 4, LocalDateTime.of(2019, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6 = new Meal(ID_1 + 5, LocalDateTime.of(2019, Month.MAY, 31, 18, 0), "Ужин", 510);
    public static final List<Meal> MEALS = new ArrayList<>();

    static {
        MEALS.add(MEAL6);
        MEALS.add(MEAL5);
        MEALS.add(MEAL4);
        MEALS.add(MEAL3);
        MEALS.add(MEAL2);
        MEALS.add(MEAL1);
    }
}
