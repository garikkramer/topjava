package ru.javawebinar.topjava.dao;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class DataMealInListImplTest {
    @org.junit.Test
    public void getAllMeals() {
        MealDao dao = DataMealInListImpl.getInstance();
        Assert.assertTrue(dao.getAllMeals().size() == 7);
    }

    @org.junit.Test
    public void addMeal() {
        MealDao dao = DataMealInListImpl.getInstance();
        Meal meal = new Meal(6, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        dao.addMeal(meal);
        Assert.assertTrue(meal.equals(dao.getMealById(6)));
    }

    @org.junit.Test
    public void deleteMeal() {
        MealDao dao = DataMealInListImpl.getInstance();
        dao.deleteMeal(0);
        Assert.assertNull(dao.getMealById(0));
    }

    @org.junit.Test
    public void updateMeal() {
        MealDao dao = DataMealInListImpl.getInstance();
        Meal meal = dao.getMealById(0);
        Meal updateMeal = new Meal(meal.getId(), meal.getDateTime(), null, 0);
        dao.updateMeal(updateMeal);
        Assert.assertFalse(meal.equals(dao.getMealById(0)));
    }

    @org.junit.Test
    public void getMealById() {
        MealDao dao = DataMealInListImpl.getInstance();
        Meal meal = dao.getMealById(0);
        Assert.assertTrue(meal.equals(dao.getAllMeals().get(0)));
    }

}