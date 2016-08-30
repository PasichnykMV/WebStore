package com.sombra.services;

import com.sombra.dao.LotDao;
import com.sombra.model.Lot;

import java.util.List;
import java.util.Set;

/**
 * Created by Макс on 18.08.2016.
 */
public interface LotService extends GenericService<Lot,LotDao> {
    List<Lot> getAllLotsInUserCart(Integer userId);
    void deleteLotFromCart(Integer userId, Integer lotId);
    void deleteAllFromCart(Integer userId);
    void addLotToUsersOrder(Integer userId, Integer lotId);
    List<Lot> getAllForCategory(Set<Integer> set, Integer categoryId);
    List<Lot> getAllUsersOrders(Integer userId);
}
