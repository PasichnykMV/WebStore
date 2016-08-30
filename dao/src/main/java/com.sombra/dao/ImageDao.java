package com.sombra.dao;

import com.sombra.model.Image;

/**
 * Created by Макс on 16.08.2016.
 */
public interface ImageDao extends AbstractDao<Image>{
    void addLotImages(Integer lotId, Integer imageId);
}
