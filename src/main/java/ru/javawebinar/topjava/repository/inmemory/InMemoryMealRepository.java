package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }
        // treat case: update, but not present in storage
        //return repo.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        repo.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
        return repo.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
        return repo.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
        return repo.get(userId).values() == null ? new ArrayList<Meal>() : new ArrayList<Meal>(repo.get(userId).values());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate from, LocalDate to) {
        repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
        return getAll(userId).stream()
                .filter(e -> DateTimeUtil.isBetween(e.getDate(), from, to))
                .collect(Collectors.toList());
    }
}

