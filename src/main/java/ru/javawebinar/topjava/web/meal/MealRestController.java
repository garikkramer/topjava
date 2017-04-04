package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    private Sorter sorter = Sorter.DATE_TIME;
    private LocalDate startDate = null;
    private LocalDate endDate   = null;
    private LocalTime startTime = null;
    private LocalTime endTime   = null;

    public Meal save(Meal meal) {
        return service.save(meal);
    }

    public void delete(int id) throws NotFoundException {
        service.delete(id);
    }

    public Meal get(int id) throws NotFoundException {
        return service.get(id);
    }

    public Collection<MealWithExceed> getAll() {
        Collection<Meal> meals = service.getAll(constructPredicate());
        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(meals,
                startTime != null? startTime: LocalTime.MIN,
                endTime != null? endTime: LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay());
        return mealWithExceedList.stream()
                .sorted(constructComparator())
                .collect(Collectors.toList());
    }

    public void update(Meal meal) {
        service.save(meal);
    }

    private Comparator<MealWithExceed> constructComparator(){
        switch (sorter){
            case DATE:
                return Comparator.comparing(MealWithExceed::getDate);
            case TIME:
                return Comparator.comparing(MealWithExceed::getTime);
        }
        return Comparator.comparing(MealWithExceed::getDateTime);
    }

    private Predicate<Meal> constructPredicate(){
        LocalDate startDate = this.startDate != null? this.startDate: LocalDate.MIN;
        LocalDate endDate = this.endDate != null? this.endDate: LocalDate.MAX;
        return t -> DateTimeUtil.isBetween(t.getDate(), startDate, endDate);
    }

    public enum Sorter{
        DATE_TIME,
        DATE,
        TIME
    }

    public Sorter getSorter() {
        return sorter;
    }

    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}