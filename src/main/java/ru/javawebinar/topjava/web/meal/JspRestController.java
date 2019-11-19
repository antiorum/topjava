package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class JspRestController extends AbstractMealController {
    public JspRestController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getMeals(Model model){
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping
    public String postMeals(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(request.getParameter("id")));
        }
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/delete/id={id}")
    public String deleteMeal(@PathVariable int id){
        super.delete(id);
        return "redirect:http://localhost:8080/topjava/meals";
    }

    @GetMapping("/update/id={id}")
    public String updateMeal(@PathVariable int id, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = super.get(id);
        model.addAttribute("title","update");
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String createMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("title","create");
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filteredMeals(@RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam String startTime,
                                @RequestParam String endTime,
                                Model model){
        LocalDate date1 = DateTimeUtil.parseLocalDate(startDate);
        LocalDate date2 = DateTimeUtil.parseLocalDate(endDate);
        LocalTime time1 = DateTimeUtil.parseLocalTime(startTime);
        LocalTime time2 = DateTimeUtil.parseLocalTime(endTime);
        model.addAttribute("meals", super.getBetween(date1, time1, date2, time2));
        return "meals";
    }
}
