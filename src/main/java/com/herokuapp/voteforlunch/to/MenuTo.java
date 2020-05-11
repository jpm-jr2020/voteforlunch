package com.herokuapp.voteforlunch.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MenuTo {
    private Restaurant restaurant;
    private Map<LocalDate, List<DishTo>> menus;

    public MenuTo(Restaurant restaurant, List<Dish> dishes) {
        this.restaurant = restaurant;
        this.menus = loadMenus(dishes);
    }

    @JsonCreator
    public MenuTo(@JsonProperty("restaurant") Restaurant restaurant, @JsonProperty("menus") Map<LocalDate, List<DishTo>> menus) {
        this.restaurant = restaurant;
        this.menus = new HashMap<>(menus);
    }

    private Map<LocalDate, List<DishTo>> loadMenus(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingBy(Dish::getDate, HashMap::new,
                Collectors.mapping(DishTo::new, Collectors.toList())));
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "restaurant=" + restaurant +
                ", menus=" + menus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return Objects.equals(restaurant, menuTo.restaurant) &&
                Objects.equals(menus, menuTo.menus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant, menus);
    }
}
