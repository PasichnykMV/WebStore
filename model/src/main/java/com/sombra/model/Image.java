package com.sombra.model;

import java.io.Serializable;

/**
 * Created by PasichnykMV on 16.08.2016.
 */
public class Image implements Serializable {

    private Integer id;
    private String name;
    private String file;
    private Boolean is_cover;

    public Boolean is_cover() {
        return is_cover;
    }

    public void setIs_cover(Boolean is_cover) {
        this.is_cover = is_cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

        Image image = (Image) o;

        if (!getId().equals(image.getId())) return false;
        return getName().equals(image.getName());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

}
