package com.herokuapp.voteforlunch.model;

import org.hibernate.Hibernate;
import org.springframework.util.Assert;
import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity {
    public static final int START_SEQ = 100_000;

    public static final int NUM_OF_USERS = 3;
    public static final int NUM_OF_RESTAURANTS = 3;

    public static final int NUM_OF_DISHES_1 = 6;
    public static final int NUM_OF_DISHES_2 = 6;
    public static final int NUM_OF_DISHES_3 = 6;
    public static final int NUM_OF_DISHES = NUM_OF_DISHES_1 + NUM_OF_DISHES_2 + NUM_OF_DISHES_3;


    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Long id;

    protected AbstractEntity() {
    }

    public AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long id() {
        Assert.notNull(id, "Entity must has id");
        return id;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase() + " with id = " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.intValue();
    }
}
