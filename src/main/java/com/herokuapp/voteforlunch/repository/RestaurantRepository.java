package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    // 2.1. Список всех ресторанов
    List<Restaurant> getAll();

    // 2.2. Конкретный ресторан
    Restaurant get(long id);

    // 2.3. Создать ресторан
    // 2.4. Обновить ресторан
    Restaurant save(Restaurant restaurant);

    // 2.5. Удалить ресторан
    boolean delete(long id);
}
