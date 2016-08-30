package com.sombra.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Макс on 16.08.2016.
 */
public class City implements Serializable {
    private Integer id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!getId().equals(city.getId())) return false;
        return getName().equals(city.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
