package com.herokuapp.voteforlunch.repository.vote;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.restaurant.CrudRestaurantRepository;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public List<Vote> getBetween(long userId, LocalDate startDate, LocalDate endDate) {
        return crudVoteRepository.getBetween(userId, DateTimeUtil.dateToStartOfDay(startDate), DateTimeUtil.dateToStartOfNextDay(endDate));
    }

    @Override
    @Transactional
    public Vote save(Vote vote, long restaurantId) {
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote get(long userId, LocalDate date) {
        List<Vote> votes = crudVoteRepository.getBetween(userId, DateTimeUtil.dateToStartOfDay(date), DateTimeUtil.dateToStartOfNextDay(date));
        if (!votes.isEmpty()) {
            Vote vote = votes.get(0);
            vote.setRestaurant(crudRestaurantRepository.getWithMenus(vote.getRestaurant().getId(), date));
            return vote;
        } else return null;
    }

    @Override
    public Long getRestaurantId(long userId, LocalDate date) {
        Long restaurantId = crudVoteRepository.getRestaurantId(userId, DateTimeUtil.dateToStartOfDay(date), DateTimeUtil.dateToStartOfNextDay(date));
        return restaurantId;
    }
}
