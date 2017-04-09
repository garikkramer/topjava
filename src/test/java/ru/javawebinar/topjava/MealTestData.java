package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    public static final LocalDate DAY = LocalDate.of(2015, Month.MAY, 30);
    
    public static final Integer USER_MEAL_ID_0 = ADMIN_ID + 1;
    public static final Integer USER_MEAL_ID_1 = USER_MEAL_ID_0 + 1;
    public static final Integer USER_MEAL_ID_2 = USER_MEAL_ID_1 + 1;
    public static final Integer USER_MEAL_ID_3 = USER_MEAL_ID_2 + 1;
    public static final Integer USER_MEAL_ID_4 = USER_MEAL_ID_3 + 1;
    public static final Integer USER_MEAL_ID_5 = USER_MEAL_ID_4 + 1;
    public static final Integer ADMIN_MEAL_ID_6 = USER_MEAL_ID_5 + 1;
    public static final Integer ADMIN_MEAL_ID_7 = ADMIN_MEAL_ID_6 + 1;
    public static final Integer ADMIN_MEAL_ID_8 = ADMIN_MEAL_ID_7 + 1;

    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(USER_MEAL_ID_0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(USER_MEAL_ID_1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(USER_MEAL_ID_2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(USER_MEAL_ID_3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(USER_MEAL_ID_4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(USER_MEAL_ID_5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(ADMIN_MEAL_ID_6, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1010),
            new Meal(ADMIN_MEAL_ID_7, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(ADMIN_MEAL_ID_8, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static List<Meal> getAllWithoutId(Integer ID, boolean isUserFood){
        List<Meal> result;
        if (isUserFood) {
            result = new ArrayList<>(MEALS_USER);
            result.remove(ID - USER_MEAL_ID_0);
        } else {
            result = new ArrayList<>(MEALS_ADMIN);
            result.remove(ID - ADMIN_MEAL_ID_6);
        }


        return getSortedList(result);
    }

    public static List<Meal> getAllWithMeal(Meal meal, boolean isUserFood){
        List<Meal> result;
        if (isUserFood)
            result = new ArrayList<>(MEALS_USER);
        else
            result = new ArrayList<>(MEALS_ADMIN);

        result.add(meal);
        return getSortedList(result);
    }

    public static Meal getById(int id, boolean isUserFood){
        if(isUserFood){
            return MEALS_USER.get(id - USER_MEAL_ID_0);
        }
        return MEALS_ADMIN.get(id - ADMIN_MEAL_ID_6);
    }

    public static List<Meal> getSortedList(List<Meal> meals){
        return meals.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    public static List<Meal> getMealsisBetween(LocalDate start, LocalDate end, boolean isUserFood){
        List<Meal> result;
        if (isUserFood)
            result = new ArrayList<>(MEALS_USER);
        else
            result = new ArrayList<>(MEALS_ADMIN);
        result = result.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), start, end))
                .collect(Collectors.toList());
        return getSortedList(result);
    }

}
