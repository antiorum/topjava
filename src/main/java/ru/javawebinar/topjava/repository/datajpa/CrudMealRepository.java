package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Meal m where m.id=?1 and m.user.id=?2")
    int delete(int id, int user_id);

    @Query("select m from Meal m where m.id=?1 and m.user.id=?2")
    Meal getByUserId(int id, int user_id);

    List<Meal> getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUser_IdOrderByDateTimeDesc(LocalDateTime start, LocalDateTime end, int userId);

    List<Meal> getAllByUser_IdOrderByDateTimeDesc(int userId);

    @Query("select m from Meal m left join fetch m.user where m.id=?1 and m.user.id=?2")
    Meal getWithUser(int id, int userId);
}
