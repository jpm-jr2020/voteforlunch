package com.herokuapp.voteforlunch.repository.dish;

import com.herokuapp.voteforlunch.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {

    // 2.6. Все меню конкретного ресторана
    List<Dish> getAll(long restaurantId);

    // 2.7. Все меню конкретного ресторана за период
    List<Dish> getBetween(long restaurantId, LocalDate startDate, LocalDate endDate);

    // 2.8. Меню конкретного ресторана на дату
    List<Dish> getByDate(long restaurantId, LocalDate date);

    // 2.9. Блюдо в меню конкретного ресторана на дату
    Dish get(long id, long restaurantId, LocalDate date);

    // 2.10. Добавить блюдо в меню конкретного ресторана на дату
    // 2.12. Обновить блюдо в меню конкретного ресторана на дату
    Dish save(Dish dish, long restaurantId, LocalDate date);

    // 2.11. Удалить блюдо из меню конкретного ресторана на дату
    boolean delete(long id, long restaurantId, LocalDate date);

    boolean existsById(long id);
}

