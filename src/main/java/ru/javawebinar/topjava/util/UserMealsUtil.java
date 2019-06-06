package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * Реализовать метод UserMealsUtil.getFilteredWithExceeded через циклы (`forEach`):
 * -  должны возвращаться только записи между startTime и endTime
 * -  поле UserMealWithExceed.exceed должно показывать,
 *                                      превышает ли сумма калорий за весь день параметра метода caloriesPerDay
 *
 * Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.
 */

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(
                mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println(filteredWithExceeded);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMealWithExceed> list = new ArrayList<>();

        mealList.forEach(userMeal -> {
            LocalDate curDate = userMeal.getDateTime().toLocalDate();
            map.put(curDate, userMeal.getCalories() + map.getOrDefault(curDate, 0));}
            );

        mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(userMeal -> list.add(
                        new UserMealWithExceed(
                                userMeal,
                                map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                );

        return list;
    }
}
