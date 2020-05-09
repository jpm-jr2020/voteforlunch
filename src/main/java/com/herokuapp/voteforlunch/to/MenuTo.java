package com.herokuapp.voteforlunch.to;

import com.herokuapp.voteforlunch.model.Dish;
import com.herokuapp.voteforlunch.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MenuTo {
    private Restaurant restaurant;
    private Map<LocalDate, Set<DishTo>> menus;

    public MenuTo(Restaurant restaurant, List<Dish> dishes) {
        this.restaurant = restaurant;
        this.menus = loadMenus(dishes);
    }

    private Map<LocalDate, Set<DishTo>> loadMenus(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingBy(Dish::getDate,
                Collectors.mapping(DishTo::new, Collectors.toCollection(TreeSet::new))));
    }
}
