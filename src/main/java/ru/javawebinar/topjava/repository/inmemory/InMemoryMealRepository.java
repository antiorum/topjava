package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Map<Integer, Meal>> repo = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.MEALS) {
            save(meal, 1);
        }
        for (Meal meal : MealsUtil.MEALS) {
            save(meal, 2);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }
        // treat case: update, but not present in storage
        //return repo.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        return repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getWithPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate from, LocalDate to) {
        return getWithPredicate(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), from, to));
    }

    private List<Meal> getWithPredicate (int userId, Predicate<Meal> filter){
        return repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .filter(filter)
                .collect(Collectors.toList());
    }
}

