package app.repository;

import app.domain.Meal;

public interface MealsRepository {

    void saveMeal(Meal meal);
    Meal findMealByName(String name);
    void removeMealByName(String name);
}
