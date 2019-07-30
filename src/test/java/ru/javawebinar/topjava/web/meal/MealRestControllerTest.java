package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEALS_TO));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/rest/meals/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/rest/meals/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(
                mealService.getAll(USER_ID),
                MEALS.stream().filter(meal -> meal.getId() != MEAL1_ID).collect(Collectors.toList()));
    }

    @Test
    void testCreate() throws Exception {
        Meal expected = getCreated();
        List<Meal> meals = MEALS.stream().collect(Collectors.toList());
        meals.add(0, expected);

        ResultActions actions = mockMvc.perform(
                post("/rest/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(actions, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(USER_ID), meals);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(
                put("/rest/meals/" + MEAL1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void testGetBetween() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/rest/meals/between?startDate=2015-05-30&startTime=&endDate=2015-05-30&endTime="))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        List<MealTo> returned = TestUtil.readListFromJsonMvcResult(result, MealTo.class);
        assertMatch(returned, MEAL_TO3, MEAL_TO2, MEAL_TO1);
    }
}