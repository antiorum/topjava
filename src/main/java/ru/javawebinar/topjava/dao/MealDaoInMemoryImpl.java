package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemoryImpl implements MealDao {
    public static AtomicLong idCounter = new AtomicLong(0);
    private ConcurrentHashMap<Long,Meal> mealRepository = new ConcurrentHashMap<>();

    @Override
    public List<Meal> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(mealRepository.values()));
    }

    @Override
    public Meal findById(Long id) {
        return mealRepository.get(id);
    }

    @Override
    public void delete(Long id) {
        mealRepository.remove(id);
    }

    @Override
    public void create(Meal meal) {
        mealRepository.put(meal.getId(),meal);
    }

    @Override
    public void update(Meal meal) {
        delete(meal.getId());
        create(meal);
    }
}
