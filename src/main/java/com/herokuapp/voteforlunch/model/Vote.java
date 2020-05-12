package com.herokuapp.voteforlunch.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.herokuapp.voteforlunch.util.DateTimeUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@JsonIgnoreProperties({"id", "userId"})
public class Vote extends AbstractEntity {
    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    // https://stackoverflow.com/questions/6311776/hibernate-foreign-keys-instead-of-entities
    @Column(name = "fk_user_id", nullable = false)
    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote() {
//        this.dateTime = LocalDateTime.of(2012,12,12,12,12);
    }

    @JsonCreator
    public Vote(@JsonProperty("id") Long id, @JsonProperty("dateTime") LocalDateTime dateTime,
                @JsonProperty("userId") long userId, @JsonProperty("restaurant") Restaurant restaurant) {
        super(id);
//        this.dateTime = LocalDateTime.of(2011,11,11,11,11);
        this.dateTime = dateTime;
        this.userId = userId;
        this.restaurant = restaurant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
//        this.dateTime = LocalDateTime.of(2010,10,10,10,10);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString() +
                " пользователь " + userId +
                " в " + dateTime +
                " голосовал за ресторан " + restaurant;
    }
}
