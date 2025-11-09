package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Cabinet;

import java.util.List;

public interface CabinetRepo {
    public List<Cabinet> findAll() throws DaoException;
    public Cabinet findById(Long identity) throws DaoException;
    public Cabinet save(Cabinet newElement) throws DaoException;
    public void update(Cabinet newValuesElement) throws DaoException;
    public void delete(Cabinet element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
