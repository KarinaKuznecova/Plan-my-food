package app.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meal {

    private String name;
    private Map<Ingredient, Double> ingredients = new HashMap<>();
    private List<MealTag> tags;
}
