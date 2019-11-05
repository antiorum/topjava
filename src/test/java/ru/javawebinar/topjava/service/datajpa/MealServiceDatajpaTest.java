package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.DATAJPA})
public class MealServiceDatajpaTest extends MealServiceTest {

    @Test
    public void getMealWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), UserTestData.USER);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongMeal() {
        Meal m = service.getWithUser(MEAL1_ID, ADMIN_ID);
    }
}
