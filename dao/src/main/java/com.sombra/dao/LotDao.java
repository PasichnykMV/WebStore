package com.sombra.dao;

import com.sombra.model.Lot;

import java.util.List;
import java.util.Set;

/**
 * Created by Макс on 16.08.2016.
 */
public interface LotDao extends AbstractDao<Lot>{
    List<Lot> getAllLotsInUserCart (Integer userId);
    void deleteLotFromCart (Integer userId, Integer lotId);
    void deleteAllFromCart (Integer userId);
    void addLotToUsersOrder(Integer userId, Integer lotId);
    void fetchImages(Lot lot);
    List<Lot> getAllForCategory (Set<Integer> set, Integer categoryId);
    List<Lot> getAllUsersOrders(Integer userId);
}
