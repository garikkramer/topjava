package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    public void addMeal(Meal meal);
    public void deleteMeal(int id);
    public void updateMeal(Meal meal);
    public Meal getMealById(int id);
    public List<Meal> getAllMeals();
}
