package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealDaoInMemoryImpl implements MealDao {
    private CopyOnWriteArrayList<Meal> mealRepository = new CopyOnWriteArrayList<>();

    @Override
    public List<Meal> findAll() {
        return mealRepository;
    }

    @Override
    public Meal findById(Long id) {
        for (Meal meal: mealRepository){
            if (meal.getId().equals(id)) return meal;
        }
        return null;
    }

    @Override
    public void delete(Meal meal) {
        mealRepository.remove(meal);
    }

    @Override
    public void create(Meal meal) {
        mealRepository.add(meal);
    }
}
