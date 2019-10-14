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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);


    @Autowired
    private MealService service;

    public List<MealTo> getAllWithUser() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getMealsWithTimeFilter(HttpServletRequest request) {
        LocalDate fromDate = request.getParameter("dateFrom").isEmpty() ? LocalDate.MIN : LocalDate.parse(request.getParameter("dateFrom"));
        LocalDate toDate = request.getParameter("dateTo").isEmpty() ? LocalDate.MAX : LocalDate.parse(request.getParameter("dateTo"));
        LocalTime fromTime = request.getParameter("timeFrom").isEmpty() ? LocalTime.MIN : LocalTime.parse(request.getParameter("timeFrom"));
        LocalTime toTime = request.getParameter("timeTo").isEmpty() ? LocalTime.MAX : LocalTime.parse(request.getParameter("timeTo"));
        return MealsUtil.getFilteredTos(service.getFilteredByDate(SecurityUtil.authUserId(), fromDate, toDate), SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime);
    }

    public Meal getMeal(HttpServletRequest request) {
        return service.get(getId(request), SecurityUtil.authUserId());
    }

    public void create(Meal meal) {
        service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(HttpServletRequest request) {
        service.delete(getId(request), SecurityUtil.authUserId());
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public Meal createOrUpdate(HttpServletRequest request) {
        String action = request.getParameter("action");
        return "create".equals(action) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                getMeal(request);
    }

    public void post(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String idFromRequest = request.getParameter("id");
        Meal meal = new Meal(idFromRequest.isEmpty() ? null : Integer.parseInt(idFromRequest),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal, Integer.parseInt(idFromRequest));
        }
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
    }
}