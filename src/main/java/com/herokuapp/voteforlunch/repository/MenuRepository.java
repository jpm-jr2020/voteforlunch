//package com.herokuapp.voteforlunch.repository;
//
//import com.herokuapp.voteforlunch.model.Menu;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface MenuRepository {
//
//    // 1.2. Список всех ресторанов и их меню на сегодня
//    List<Menu> getEverywhereToday();
//
//    // 1.3. Конкретный ресторан и его меню на сегодня
//    // 2.8. Меню конкретного ресторана на дату
//    Menu getOne(long id, long restaurantId);
//    Menu getOneByDate(long restaurantId, LocalDate date);
//
//    // 2.7. Меню конкретного ресторана за период
//    List<Menu> getAllByPeriod(long restaurantId, LocalDate startDate, LocalDate endDate);
//
//    // 2.6. Меню конкретного ресторана
//    List<Menu> getAll(long restaurantId);
//
//    // 2.9. Создать меню конкретного ресторана на дату
//    // 2.10. Обновить меню конкретного ресторана на дату
//    Menu save(Menu menu, long restaurantId);
//
//    // 2.11. Удалить меню конкретного ресторана на дату
//    boolean delete(long id, long restaurantId);
//
//    // 2.15. Очистить меню конкретного ресторана на дату
//    boolean clear(long id, long restaurantId);
//}
