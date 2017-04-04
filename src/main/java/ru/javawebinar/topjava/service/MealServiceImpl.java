package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        if(!repository.delete(id))
            throw new NotFoundException("Error delete");
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFound(repository.get(id), "Нет такого элемента");
    }

    @Override
    public Collection<Meal> getAll(Predicate<Meal> predicate) {
        Collection result = repository.getAll(predicate);
        return (result == null || result.isEmpty())?Collections.emptyList():result;
    }

}