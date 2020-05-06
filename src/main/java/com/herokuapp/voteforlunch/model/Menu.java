//package com.herokuapp.voteforlunch.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.herokuapp.voteforlunch.util.DateTimeUtil;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDate;
//import java.util.Set;
//
//@Entity
//@Table(name = "menus")
//public class Menu extends AbstractEntity {
//
//    @Column(name = "date", nullable = false)
//    @NotNull
//    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
//    private LocalDate date;
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OrderBy("name ASC")
//    @JsonManagedReference
//    private Set<Dish> dishes;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_restaurant_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
//    private Restaurant restaurant;
//
////    public void setDishes(Set<Dish> dishes) {
////        this.dishes = dishes;
////    }
//
//    public Menu() {
//    }
//
//    public Menu(Long id, LocalDate date) {
//        super(id);
//        this.date = date;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public Set<Dish> getDishes() {
//        return dishes;
//    }
//
//    public Restaurant getRestaurant() {
//        return restaurant;
//    }
//
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }
//}
