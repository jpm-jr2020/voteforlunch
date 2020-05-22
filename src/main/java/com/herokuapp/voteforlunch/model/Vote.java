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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
@JsonIgnoreProperties({"id", "userId"})
public class Vote extends AbstractEntity {
    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.TIME_PATTERN)
    private LocalTime time;

    // https://stackoverflow.com/questions/6311776/hibernate-foreign-keys-instead-of-entities
    @Column(name = "fk_user_id", nullable = false)
    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote() {
    }

    @JsonCreator
    public Vote(@JsonProperty("id") Long id, @JsonProperty("date") LocalDate date, @JsonProperty("time") LocalTime time,
                @JsonProperty("userId") long userId, @JsonProperty("restaurant") Restaurant restaurant) {
        super(id);
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    public void setDateTime(LocalDateTime dateTime) {
        date = dateTime.toLocalDate();
        time = dateTime.toLocalTime();
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
                " " + date + " в " + time +
                " голосовал за ресторан " + restaurant;
    }
}
