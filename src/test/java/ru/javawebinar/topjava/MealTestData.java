package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int COUNTER = START_SEQ;

    public static final Meal MEAL1 = new Meal(COUNTER + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 10, 10), "breakfast", 666);
    public static final Meal MEAL2 = new Meal(COUNTER + 3, LocalDateTime.of(2015, Month.MAY, 30, 20, 10, 10), "dinner", 777);
    public static final Meal MEAL3 = new Meal(COUNTER + 4, LocalDateTime.of(2015, Month.MAY, 31, 10, 11, 10), "breakfast", 888);
    public static final Meal MEAL4 = new Meal(COUNTER + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 11, 10), "dinner", 999);
    public static final Meal MEAL5 = new Meal(COUNTER + 6, LocalDateTime.of(2019, Month.OCTOBER, 19, 20, 0, 0), "dinner", 578);
    public static final Meal MEAL6 = new Meal(COUNTER + 7, LocalDateTime.of(2019, Month.OCTOBER, 19, 10, 0, 0), "beer", 1488);
}
