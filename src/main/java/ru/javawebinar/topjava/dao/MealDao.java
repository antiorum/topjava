package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    List<Meal> findAll();
    Meal findById(Long id);
    void delete(Meal meal);
    void create(Meal meal);
}
