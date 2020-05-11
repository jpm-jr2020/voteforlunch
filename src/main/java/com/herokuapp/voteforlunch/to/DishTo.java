package com.herokuapp.voteforlunch.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.model.Dish;

import javax.validation.constraints.PositiveOrZero;

public class DishTo extends AbstractTo implements Comparable<DishTo> {
    private Integer price;

    @JsonCreator
    public DishTo(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("price") Integer price) {
        super(id, name);
        this.price = price;
    }

    @JsonCreator
    public DishTo(@JsonProperty("name") String name, @JsonProperty("price") Integer price) {
        this(null, name, price);
    }

    public DishTo(Dish dish) {
        super(dish.getId(), dish.getName());
        this.price = dish.getPrice();
    }

    @PositiveOrZero
    public Integer getPrice() {
        return price;
    }

    @Override
    public int compareTo(DishTo o) {
        return name.compareTo(o.name);
    }
}
