package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Charge;

import java.util.List;

public interface ChargeRepo {

    public List<Charge> findAll() throws DaoException;
    public Charge findById(Long identity) throws DaoException;
    public Charge save(Charge newElement) throws DaoException;
    public void update(Charge newValuesElement) throws DaoException;
    public void delete(Charge element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
