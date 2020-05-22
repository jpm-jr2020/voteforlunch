package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.userId=:userId ORDER BY v.date DESC")
    List<Vote> getAll(@Param("userId") long userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.userId=:userId AND v.date >= :startDate AND v.date <= :endDate ORDER BY v.date DESC")
    List<Vote> getBetween(@Param("userId") long userId,
                          @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT v FROM Vote v WHERE v.userId=:userId AND v.date = :date")
    Vote get(@Param("userId") long userId, @Param("date") LocalDate date);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.userId=:userId AND v.date = :date")
    Long getRestaurantId(@Param("userId") long userId, @Param("date") LocalDate date);
}
