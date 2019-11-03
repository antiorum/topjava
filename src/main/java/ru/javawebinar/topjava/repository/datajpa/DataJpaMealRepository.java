package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private UserRepository userRepo;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepo.get(userId);
        if (!meal.isNew() && get(meal.getId(), userId) == null) return null;
        meal.setUser(user);
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User u = new User();
        u.setId(userId);
        return crudRepository.getAllByUserOrderByDateTimeDesc(u);
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        LocalDateTime start = DateTimeUtil.getStartInclusive(startDate);
        LocalDateTime end = DateTimeUtil.getEndExclusive(endDate);
        User u = new User();
        u.setId(userId);
        return crudRepository.getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserOrderByDateTimeDesc(start, end, u);
    }

    @Transactional
    @Override
    public Meal getMealWithUser(int id, int userId) {
        User u = userRepo.get(userId);
        Meal meal = get(id, userId);
        meal.setUser(u);
        return meal;
    }
}
