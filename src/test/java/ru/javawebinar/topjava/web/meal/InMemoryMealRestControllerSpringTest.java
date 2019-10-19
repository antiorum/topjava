package ru.javawebinar.topjava.web.meal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

@ContextConfiguration("classpath:spring-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryMealRestControllerSpringTest {
    @Autowired
    private MealRestController mealRestController;

    @Autowired
    private InMemoryMealRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void delete () throws Exception {
        mealRestController.delete(3);
        List<MealTo> mealTos = mealRestController.getAll();
        Assert.assertEquals(5, mealTos.size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        mealRestController.delete(10);
    }
}
