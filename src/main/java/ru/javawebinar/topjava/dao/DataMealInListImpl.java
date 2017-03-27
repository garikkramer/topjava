package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DataMealInListImpl implements MealDao {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static DataMealInListImpl ourInstance = new DataMealInListImpl();
    private final List<Meal> meals = new CopyOnWriteArrayList<>();


    public static DataMealInListImpl getInstance() {
        return ourInstance;
    }

    private DataMealInListImpl() {
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAllMeals() {
        return meals;
    }

    @Override
    public void addMeal(Meal meal) {
        if (!meals.contains(meal)) {
            Meal newMeal = new Meal(counter.getAndIncrement(),
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories());
            meals.add(newMeal);
        }
    }

    @Override
    public void deleteMeal(int id) {
        Meal meal = getMealById(id);
        if(meal != null)
            meals.remove(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        Meal currentMeal = getMealById(meal.getId());
        if (currentMeal != null){
            meals.remove(currentMeal);
            meals.add(meal);
        }
    }

    @Override
    public Meal getMealById(int id) {
        return meals.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }
}
