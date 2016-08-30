package com.sombra.services;

import com.sombra.jdbc.JdbcCityDao;
import com.sombra.model.City;

/**
 * Created by PasichnykMV on 22.08.2016.
 */
public interface CityService extends GenericService<City, JdbcCityDao> {
}
