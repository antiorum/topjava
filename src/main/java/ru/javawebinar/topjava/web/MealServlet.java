package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    public static DateTimeFormatter formatter;
    private MealDao mealDao;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        mealDao = new MealDaoInMemoryImpl();

        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        request.setAttribute("formatter",formatter);
        if (action == null) {
            viewMeals(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            Long id = Long.parseLong(request.getParameter("mealId"));
            mealDao.delete(id);
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")) {
            Meal meal = mealDao.findById(Long.parseLong(request.getParameter("mealId")));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("add")) {
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals post");
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (Long.parseLong(request.getParameter("id")) > 0) {
            Long id = Long.parseLong(request.getParameter("id"));
            mealDao.update(new Meal(id, dateTime, description, calories));
        } else {
            Meal meal = new Meal(dateTime, description, calories);
            mealDao.create(meal);
        }
        response.sendRedirect("meals");
    }

    private void viewMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsWithExceed = MealsUtil.getFiltered(mealDao.findAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        request.setAttribute("meals", mealsWithExceed);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
