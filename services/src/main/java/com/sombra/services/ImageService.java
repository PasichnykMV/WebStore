package com.sombra.services;

import com.sombra.dao.ImageDao;
import com.sombra.model.Image;

/**
 * Created by PasichnykMV on 23.08.2016.
 */
public interface ImageService extends GenericService<Image, ImageDao> {
    void addLotImages(Integer lotId, Integer imageId);
}
