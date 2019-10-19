package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int COUNTER = START_SEQ;

    public static final Meal MEAL1 = new Meal(COUNTER, LocalDateTime.of(2015, Month.MAY, 30, 10, 10, 10), "breakfast", 666);
    public static final Meal MEAL2 = new Meal(COUNTER+1, LocalDateTime.of(2015, Month.MAY, 30, 20, 10, 10), "dinner", 777);
    public static final Meal MEAL3 = new Meal(COUNTER+2, LocalDateTime.of(2015, Month.MAY, 31, 10, 11, 10), "breakfast", 888);
    public static final Meal MEAL4 = new Meal(COUNTER+3, LocalDateTime.of(2015, Month.MAY, 31, 20, 11, 10), "dinner", 999);
}
