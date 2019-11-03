package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Meal m where m.user.id=:user_id and m.id=:id")
    int delete(@Param("id") int id, @Param("user_id") int user_id);

    @Query("select m from Meal m where m.user.id=:user_id and m.id=:id")
    Meal getByUserId(@Param("id")int id, @Param("user_id") int user_id );

    List<Meal> getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserOrderByDateTimeDesc (LocalDateTime start,
                                                                                              LocalDateTime end,
                                                                                              User u);

    List<Meal> getAllByUserOrderByDateTimeDesc (User u);
}
