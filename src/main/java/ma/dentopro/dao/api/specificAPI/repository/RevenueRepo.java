package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Revenue;

import java.util.List;

public interface RevenueRepo {

    public List<Revenue> findAll() throws DaoException;
    public Revenue findById(Long identity) throws DaoException;
    public Revenue save(Revenue newElement) throws DaoException;
    public void update(Revenue newValuesElement) throws DaoException;
    public void delete(Revenue element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;


}
