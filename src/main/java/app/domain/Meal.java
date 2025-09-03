package app.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meal {

    String name;
    Map<Ingredient, Double> ingredients = new HashMap<>();
    List<MealTag> tags;
}
