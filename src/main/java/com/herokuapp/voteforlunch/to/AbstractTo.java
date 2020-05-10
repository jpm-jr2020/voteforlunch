package com.herokuapp.voteforlunch.to;

public abstract class AbstractTo {
    protected Long id;
    protected String name;

    public AbstractTo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
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
}
