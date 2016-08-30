package com.sombra.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Макс on 16.08.2016.
 */
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Date creationDate;
    private UserRole role;
    private boolean is_enable;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean is_enable() {
        return is_enable;
    }

    public void setEnable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (is_enable != user.is_enable) return false;
        if (!getId().equals(user.getId())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getLastName().equals(user.getLastName())) return false;
        return getCreationDate().equals(user.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, is_enable);
    }
}
