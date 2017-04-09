package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() {
        MATCHER.assertEquals(service.get(USER_MEAL_ID_3, USER_ID), getById(USER_MEAL_ID_3, true));
        MATCHER.assertEquals(service.get(ADMIN_MEAL_ID_8, ADMIN_ID), getById(ADMIN_MEAL_ID_8, false));
    }

    @Test(expected = NotFoundException.class)
    public void getNotMyMeal() {
        service.get(USER_MEAL_ID_0, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(0, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_ID_6, ADMIN_ID);
        MATCHER.assertCollectionEquals(service.getAll(ADMIN_ID), getAllWithoutId(ADMIN_MEAL_ID_6, false));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotMyMeal() {
        service.delete(ADMIN_MEAL_ID_6, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        MATCHER.assertCollectionEquals(service.getBetweenDates(DAY, DAY, ADMIN_ID), getMealsisBetween(DAY, DAY, false));
        MATCHER.assertCollectionEquals(service.getBetweenDates(DAY, DAY, USER_ID), getMealsisBetween(DAY, DAY, true));
    }

    @Test
    public void getAll() {
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), getSortedList(MEALS_USER));
        MATCHER.assertCollectionEquals(service.getAll(ADMIN_ID), getSortedList(MEALS_ADMIN));
    }

    @Test
    public void save() {
        Meal meal = new Meal(null, LocalDateTime.now(), "desc", 1000);
        Meal created = service.save(meal, UserTestData.USER_ID);
        meal.setId(created.getId());
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), getAllWithMeal(meal, true));
    }

    @Test(expected = NotFoundException.class)
    public void saveNotMyMeal() {
        Meal meal = service.get(ADMIN_MEAL_ID_7, ADMIN_ID);
        service.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotFound() {
        Meal meal = service.get(ADMIN_MEAL_ID_7, ADMIN_ID);
        meal.setId(0);
        service.update(meal, USER_ID);
    }

}