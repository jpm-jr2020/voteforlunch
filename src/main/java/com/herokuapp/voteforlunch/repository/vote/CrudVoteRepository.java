package com.herokuapp.voteforlunch.repository.vote;

import com.herokuapp.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.userId=:userId ORDER BY v.dateTime DESC")
    List<Vote> getAll(@Param("userId") long userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.userId=:userId AND v.dateTime >= :startDateTime AND v.dateTime < :endDateTime ORDER BY v.dateTime DESC")
    List<Vote> getBetween(@Param("userId") long userId,
                          @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
