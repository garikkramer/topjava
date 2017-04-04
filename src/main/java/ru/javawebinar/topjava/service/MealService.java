package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.function.Predicate;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    Collection<Meal> getAll(Predicate<Meal> predicate);

}