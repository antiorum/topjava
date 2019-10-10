package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemoryImpl implements MealDao {
    public static AtomicLong idCounter = new AtomicLong(0);
    private Map<Long,Meal> mealRepository = new ConcurrentHashMap<>();

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealRepository.values());
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
        Long newId = idCounter.incrementAndGet();
        mealRepository.put(newId,meal);
        meal.setId(newId);
    }

    @Override
    public void update(Meal meal) {
        mealRepository.put(meal.getId(),meal);
    }
}
