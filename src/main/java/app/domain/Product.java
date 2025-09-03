package app.domain;

import java.util.List;

public class Product {

    private String name;
    private MeasuringUnit defaultUnit;
    private List<ProductTag> tags;

    public Product(String name, MeasuringUnit defaultUnit, List<ProductTag> tags) {
        this.name = name;
        this.defaultUnit = defaultUnit;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

}
