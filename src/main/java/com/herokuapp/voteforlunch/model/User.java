package com.herokuapp.voteforlunch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private Boolean enabled;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    @JoinTable(name = "votes", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//      inverseJoinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
//    @MapKey(name = "restaurant_id")

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    @JoinTable(name = "votes", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//      inverseJoinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
//    @MapKeyTemporal(TemporalType.TIMESTAMP)

//    @ElementCollection
//    @CollectionTable(name = "votes", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
//    @MapKeyColumn(name = "date_time")
//    @Column(name = "restaurant_id")

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "votes", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "date_time")
    private Map<LocalDateTime, Restaurant> votes;

    public User() {
    }

    public User(Long id, String name, String email, String password, Boolean enabled, Set<Role> roles, Map<LocalDateTime, Restaurant> votes) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.votes = votes;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Map<LocalDateTime, Restaurant> getVotes() {
        return votes;
    }
}
