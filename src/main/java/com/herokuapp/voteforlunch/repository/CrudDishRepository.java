package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.date DESC")
    List<Dish> getAll(@Param("restaurantId") long restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC")
    List<Dish> getBetween(@Param("restaurantId") long restaurantId,
                          @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date = :date ORDER BY d.date DESC")
    List<Dish> getByDate(@Param("restaurantId") long restaurantId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") long id);
}
