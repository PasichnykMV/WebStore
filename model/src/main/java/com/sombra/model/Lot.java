package com.sombra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Макс on 16.08.2016.
 */
public class Lot implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private City city;
    private Category category;
    private List<Image> images = new ArrayList<>();
    private Date creationDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lot lot = (Lot) o;

        if (!getId().equals(lot.getId())) return false;
        if (!getTitle().equals(lot.getTitle())) return false;
        if (!getPrice().equals(lot.getPrice())) return false;
        if (!getCity().equals(lot.getCity())) return false;
        if (!getCategory().equals(lot.getCategory())) return false;
        return getImages().equals(lot.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }
}
