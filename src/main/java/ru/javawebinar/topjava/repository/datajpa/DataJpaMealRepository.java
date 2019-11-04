package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository userRepo;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepo.getOne(userId);
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
        return crudRepository.getAllByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        LocalDateTime start = DateTimeUtil.getStartInclusive(startDate);
        LocalDateTime end = DateTimeUtil.getEndExclusive(endDate);
        return crudRepository.getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUser_IdOrderByDateTimeDesc(start, end, userId);
    }

    @Override
    public Meal getMealWithUser(int id, int userId) {
        return crudRepository.getWithUser(id, userId);
    }
}
