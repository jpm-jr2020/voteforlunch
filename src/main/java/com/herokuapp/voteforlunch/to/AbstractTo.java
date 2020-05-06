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
}
