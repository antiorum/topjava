package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.service.MealServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.DATAJPA})
public class MealServiceDatajpaTest extends MealServiceTest {

    @Test
    public void getMealWithUser(){
        assertThat(service.getWithUser(MEAL1_ID, USER_ID).getUser()).isEqualTo(UserTestData.USER);
    }
}
