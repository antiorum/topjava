package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            try {
                if (get(meal.getId(), userId).getUser().getId() != userId) throw new NullPointerException();
            } catch (NullPointerException e) {
                return null;
            }
            return em.merge(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("userId", userId)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        LocalDate date1 = startDate.toLocalDate();
        LocalDate date2 = endDate.toLocalDate();
        if (date1.isBefore(LocalDate.of(-2000, 1, 1))) date1 = LocalDate.of(-2000, 1, 1);
        if (date2.isAfter(LocalDate.of(9999, 12, 31))) date2 = LocalDate.of(9999, 12, 31);
        LocalTime time1 = startDate.toLocalTime();
        LocalTime time2 = endDate.toLocalTime();
        if (time1.isAfter(LocalTime.of(23, 59, 59))) time2 = LocalTime.of(23, 59, 59);
        if (time2.isAfter(LocalTime.of(23, 59, 59))) time2 = LocalTime.of(23, 59, 59);
        LocalDateTime start = LocalDateTime.of(date1, time1);
        LocalDateTime end = LocalDateTime.of(date2, time2);
        System.out.println(start);
        System.out.println(end);
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("before", start)
                .setParameter("after", end)
                .getResultList();
    }
}