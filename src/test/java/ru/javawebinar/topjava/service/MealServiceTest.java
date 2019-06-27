package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(ID_1, USER_ID);
        assertThat(meal.toString()).isEqualTo(MEAL1.toString());
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        Meal meal = service.get(ID_2, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(ID_1, USER_ID);
        assertThat(service.getAll(USER_ID).size()).isEqualTo(5);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(ID_2, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        assertThat(service.getBetweenDates(LocalDate.of(2019, 5, 30), LocalDate.of(2019, 5, 30), USER_ID).size())
                .isEqualTo(3);
        assertThat(service.getBetweenDates(LocalDate.of(2019, 5, 30), LocalDate.of(2019, 5, 30), USER_ID))
                .isEqualTo(Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getBetweenDateTimes() {
        assertThat(service.getBetweenDateTimes(
                LocalDateTime.of(2019, 5, 30, 11, 0, 0), LocalDateTime.of(2019, 5, 30, 16, 0, 0), USER_ID).size())
                .isEqualTo(1);
        assertThat(service.getBetweenDateTimes(
                LocalDateTime.of(2019, 5, 30, 11, 0, 0), LocalDateTime.of(2019, 5, 30, 16, 0, 0), USER_ID))
                .isEqualTo(Arrays.asList(MEAL2));
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).isEqualTo(MEALS);
        assertThat(service.getAll(USER_ID).size()).isEqualTo(6);
    }

    @Test
    public void update() {
        Meal updated = new Meal(ID_1, LocalDateTime.of(2019, Month.MAY, 1, 10, 0), "Завтрак плюс", 490);
        service.update(updated, USER_ID);
        assertThat(service.get(ID_1, USER_ID).toString()).isEqualTo(updated.toString());
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.MAY, 1, 10, 0), "Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertThat(created.toString()).isEqualTo(newMeal.toString());
        assertThat(service.getAll(USER_ID).size()).isEqualTo(7);
    }
}