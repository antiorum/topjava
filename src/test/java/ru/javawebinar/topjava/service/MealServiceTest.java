package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
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
        Meal meal = service.get(MealTestData.COUNTER, MealTestData.COUNTER);
        assertThat(meal).isEqualTo(MEAL1);
    }

    @Test
    public void delete() {
        service.delete(100000, USER_ID);
        assertThat(service.getAll(USER_ID)).usingDefaultComparator().isEqualTo(Arrays.asList(MEAL4, MEAL3, MEAL2));
    }

    @Test
    public void getBetweenDates() {
        LocalDate dateBefore = LocalDate.of(2015, 5, 31);
        LocalDate dateAfter = LocalDate.of(2015, 6, 1);
        assertThat(service.getBetweenDates(dateBefore, dateAfter, USER_ID)).usingDefaultComparator().isEqualTo(Arrays.asList(MEAL4, MEAL3));
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).usingDefaultComparator().isEqualTo(Arrays.asList(MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void update() {
        Meal meal = new Meal(COUNTER + 3, LocalDateTime.of(2015, Month.MAY, 31, 20, 11, 10), "dinner", 222);
        service.update(meal, USER_ID);
        assertThat(meal).isEqualTo(service.get(COUNTER + 3, USER_ID));
    }

    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2017, Month.NOVEMBER, 11, 11, 11, 11), "beer", 164);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        assertThat(service.getAll(USER_ID)).usingDefaultComparator().isEqualTo(Arrays.asList(meal, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(COUNTER, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        Meal meal = new Meal(COUNTER + 3, LocalDateTime.of(2015, Month.MAY, 31, 20, 11, 10), "dinner", 222);
        service.update(meal, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        service.get(COUNTER + 3, ADMIN_ID);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateDate() {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 10, 11, 10), "breakfast", 888), USER_ID);
    }
}
