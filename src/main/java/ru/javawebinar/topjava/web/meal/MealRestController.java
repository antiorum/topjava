package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAllWithUser() {
        log.info("get all meals");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getWithTimeFilter(LocalDate fromLocalDate, LocalDate toLocalDate, LocalTime fromLocalTime, LocalTime toLocalTime) {
        log.info("get filtered meals");
        LocalDate fromDate = fromLocalDate == null ? LocalDate.MIN : fromLocalDate;
        LocalDate toDate = toLocalDate == null ? LocalDate.MAX : toLocalDate;
        LocalTime fromTime = fromLocalTime == null ? LocalTime.MIN : fromLocalTime;
        LocalTime toTime = toLocalTime == null ? LocalTime.MAX : toLocalTime;
        return MealsUtil.getFilteredTos(service.getFilteredByDate(SecurityUtil.authUserId(), fromDate, toDate), SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime);
    }

    public Meal getMeal(int id) {
        log.info("get meal with id : {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public void create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete meal with id :{}", id);
        service.delete(id, SecurityUtil.authUserId());
    }
}