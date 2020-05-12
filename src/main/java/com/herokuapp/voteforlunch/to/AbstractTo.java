package com.herokuapp.voteforlunch.to;

import org.hibernate.Hibernate;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class AbstractTo {
    @Id
    protected Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    public AbstractTo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className.substring(0, className.length() - 2).toLowerCase() + " with id = " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractTo that = (AbstractTo) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.intValue();
    }
}
