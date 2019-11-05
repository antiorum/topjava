package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(value = {Profiles.DATAJPA})
public class UserServiceDatajpaTest extends UserServiceTest {
    @Test
    public void getUserWithMeals() {
        User user = service.getWithMeal(USER_ID);
        assertMatch(user.getMeals(), MealTestData.MEALS);
        UserTestData.assertMatch(user, USER);
    }

    @Test
    public void getIfHaveNotMeals() {
        User user = new User(42, "xx", "xxx@mail.ru", "passsss", Role.ROLE_USER);
        User fromDB = service.getWithMeal(service.create(user).getId());
        assertThat(fromDB).isEqualToIgnoringGivenFields(user, "id", "meals", "registered");
        assertThat(fromDB.getMeals().size()).isZero();
    }
}
