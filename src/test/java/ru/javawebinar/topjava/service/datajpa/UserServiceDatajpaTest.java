package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(value = {Profiles.DATAJPA})
public class UserServiceDatajpaTest extends UserServiceTest {
    @Test
    public void getUserWithMeals() {
        List<Meal> meals = service.getWithMeal(USER_ID).getMeals();
        assertMatch(meals, MealTestData.MEALS);
    }

    @Test
    public void getIfHaveNotMeals() {
        User user = new User(42, "xx", "xxx@mail.ru", "passsss", Role.ROLE_USER);
        service.create(user);
        //magic number, but ok
        Assert.assertEquals(0, service.getWithMeal(100011).getMeals().size());
    }
}
