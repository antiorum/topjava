package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(value = {Profiles.DATAJPA})
public class UserServiceDatajpaTest extends UserServiceTest {
    @Test
    public void getUserWithMeals(){
        assertThat(service.getWithMeal(USER_ID).getMeals()).isEqualTo(MealTestData.MEALS);
    }
}
