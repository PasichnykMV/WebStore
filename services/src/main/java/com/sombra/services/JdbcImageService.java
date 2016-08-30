package com.sombra.services;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Image;
import java.util.List;

/**
 * Created by PasichnykMV on 23.08.2016.
 */
public class JdbcImageService implements ImageService {
    @Override
    public Image save(Image image) throws ServiceException {
        return JdbcDaoFactory.getInstance().getImageDao().persist(image);
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        JdbcDaoFactory.getInstance().getImageDao().delete(id);
    }

    @Override
    public void update(Image image) throws ServiceException {
        JdbcDaoFactory.getInstance().getImageDao().update(image);
    }

    @Override
    public Image getById(Integer id) throws ServiceException {
        return JdbcDaoFactory.getInstance().getImageDao().getById(id);
    }

    @Override
    public List<Image> getAll() throws ServiceException {
        return JdbcDaoFactory.getInstance().getImageDao().getAll();
    }

    public void addLotImages(Integer lotId, Integer imageId){
        JdbcDaoFactory.getInstance().getImageDao().addLotImages(lotId, imageId);
    }

}
