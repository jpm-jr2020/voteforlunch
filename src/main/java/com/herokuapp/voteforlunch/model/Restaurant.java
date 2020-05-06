package com.herokuapp.voteforlunch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(max = 200)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    @JsonIgnore
    private Set<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

//    WITH @MapKeyColumn
//
//  @ElementCollection
//  @CollectionTable(name = "dishes",
//        joinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
//  @MapKeyColumn(name = "name")
//  @Column(name = "price")
//  private Map<String, Integer> dishes;
//
//  public Map<String, Integer> getDishes() {
//    return dishes;
//  }
}
