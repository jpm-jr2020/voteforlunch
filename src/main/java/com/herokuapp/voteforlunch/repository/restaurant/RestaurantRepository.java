package com.herokuapp.voteforlunch.repository.restaurant;

import com.herokuapp.voteforlunch.model.Restaurant;

import java.time.LocalDate;
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

    // 1.3. Конкретный ресторан и его меню на сегодня
    Restaurant getWithMenu(long id, LocalDate date);

    // 1.2. Список всех ресторанов и их меню на сегодня
    List<Restaurant> getAllWithMenu(LocalDate date);

    boolean existsById(long id);
}
