package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Dentiste;

import java.util.List;

public interface DentisteRepo {

    public List<Dentiste> findAll() throws DaoException;
    public Dentiste findById(Long identity) throws DaoException;
    public Dentiste save(Dentiste newElement) throws DaoException;
    public void update(Dentiste newValuesElement) throws DaoException;
    public void delete(Dentiste element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;

}
