package com.herokuapp.voteforlunch.repository;

import com.herokuapp.voteforlunch.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {
    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public List<Vote> getAll(long userId) {
        return crudVoteRepository.getAll(userId);
    }

    @Override
    public List<Vote> getBetween(long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return crudVoteRepository.getBetween(userId, startDateTime, endDateTime);
    }

    @Override
    @Transactional
    public Vote save(Vote vote, long restaurantId) {
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote get(long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Vote> votes = crudVoteRepository.getBetween(userId, startDateTime, endDateTime);
        if (!votes.isEmpty()) {
            Vote vote = votes.get(0);
            vote.setRestaurant(crudRestaurantRepository.getWithMenus(vote.getRestaurant().getId(), startDateTime.toLocalDate()));
            return vote;
        } else return null;
    }
}
