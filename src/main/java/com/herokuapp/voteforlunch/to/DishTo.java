package com.herokuapp.voteforlunch.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.model.Dish;

import javax.validation.constraints.PositiveOrZero;

public class DishTo extends AbstractTo {
    private Integer price;

    @JsonCreator()
    public DishTo(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("price") Integer price) {
        super(id, name);
        this.price = price;
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
    public String toString() {
        return super.toString() +
                ", '" + name + '\'' +
                " за " + price;
    }
}
