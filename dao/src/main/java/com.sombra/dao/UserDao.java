package com.sombra.dao;

import com.sombra.model.Lot;
import com.sombra.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public interface UserDao extends AbstractDao<User> {
    Integer getIdByEmail(String email);
    void addLotToUsersCart(Integer userId, Integer lotId);
    Integer getCountLotsInUsersCart(Integer userId);
}
