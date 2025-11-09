package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Acte;

import java.util.List;

public interface ActRepo {

    public List<Acte> findAll() throws DaoException;
    public Acte findById(Long identity) throws DaoException;
    public Acte save(Acte newElement) throws DaoException;
    public void update(Acte newValuesElement) throws DaoException;
    public void delete(Acte element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
