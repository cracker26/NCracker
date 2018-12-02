package com.team26.model;

import java.io.Serializable;
import java.util.UUID;

public class Genre implements Serializable {

    private UUID id;

    private String name;

    public Genre(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
