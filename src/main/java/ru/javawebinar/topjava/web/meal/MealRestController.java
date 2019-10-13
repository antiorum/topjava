package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Qualifier("mealService")
    private MealService service;

    public List<MealTo> getAllWithUser (HttpServletRequest request, HttpServletResponse response){
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getMealsWithTimeFilter(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime){
        return MealsUtil.getFilteredTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime, fromDate, toDate);
    }

    public Meal getMeal (HttpServletRequest request, HttpServletResponse response){
        return service.get(getId(request), SecurityUtil.authUserId());
    }

    public void create (Meal meal){
        service.create(meal, SecurityUtil.authUserId());
    }

    public void update (Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public void delete (HttpServletRequest request, HttpServletResponse response){
        service.delete(getId(request),SecurityUtil.authUserId());
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                delete(request, response);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        getMeal(request,response);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                LocalDate dateFrom = request.getParameter("dateFrom").isEmpty() ? LocalDate.MIN : LocalDate.parse(request.getParameter("dateFrom"));
                LocalDate dateTo = request.getParameter("dateTo").isEmpty() ? LocalDate.MAX : LocalDate.parse(request.getParameter("dateTo"));
                LocalTime timeFrom = request.getParameter("timeFrom").isEmpty() ? LocalTime.MIN : LocalTime.parse(request.getParameter("timeFrom"));
                LocalTime timeTo = request.getParameter("timeTo").isEmpty() ? LocalTime.MAX : LocalTime.parse(request.getParameter("timeTo"));
                request.setAttribute("meals", getMealsWithTimeFilter(dateFrom, dateTo, timeFrom, timeTo));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", getAllWithUser(request, response));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String idFromRequest = request.getParameter("id");
        String userId = request.getParameter("userId");

        Meal meal = new Meal(idFromRequest.isEmpty() ? null : Integer.parseInt(idFromRequest),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                userId.isEmpty() ? SecurityUtil.authUserId() : Integer.parseInt(userId));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal, Integer.parseInt(idFromRequest));
        }
        response.sendRedirect("meals");
    }
}