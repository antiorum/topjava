package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Component;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Component
public class SecurityUtil {
    private static int userIdd = 0;

    public static int authUserId() {
        return userIdd;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setUserId(int userId) {
        userIdd = userId;
    }
}