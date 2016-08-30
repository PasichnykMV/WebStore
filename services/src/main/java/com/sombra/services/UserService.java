package com.sombra.services;

import com.sombra.jdbc.JdbcUserDao;
import com.sombra.model.User;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by PasichnykMV on 19.08.2016.
 */
public interface UserService extends GenericService<User, JdbcUserDao> {
    Integer getIdByEmail(String email);
    void addLotToUsersCart(Integer userId, Integer lotId);
    Integer getLotsIncartCount(Integer userId);
}
