package com.herokuapp.voteforlunch.repository.vote;

import com.herokuapp.voteforlunch.model.Vote;
import com.herokuapp.voteforlunch.repository.restaurant.RestaurantRepository;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {
    private final CrudVoteRepository crudVoteRepository;
    private final RestaurantRepository restaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, RestaurantRepository restaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.restaurantRepository = restaurantRepository;
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
    public Vote getWithMenu(long userId, LocalDate date) {
        Vote vote = get(userId, date);
        if (vote != null) {
            vote.setRestaurant(restaurantRepository.getWithMenu(vote.getRestaurant().getId(), date));
            return vote;
        } else return null;
    }

    @Override
    public Vote get(long userId, LocalDate date) {
        return crudVoteRepository.get(userId, DateTimeUtil.dateToStartOfDay(date), DateTimeUtil.dateToStartOfNextDay(date));
    }

    @Override
    @Transactional
    public Vote save(Vote vote, long restaurantId) {
        vote.setRestaurant(restaurantRepository.getWithMenu(restaurantId, vote.getDateTime().toLocalDate()));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Long getRestaurantId(long userId, LocalDate date) {
        return crudVoteRepository.getRestaurantId(userId, DateTimeUtil.dateToStartOfDay(date), DateTimeUtil.dateToStartOfNextDay(date));
    }
}
