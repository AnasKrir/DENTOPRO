package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Ordonnance;

import java.util.List;

public interface OrdonnanceRepo {

    public List<Ordonnance> findAll() throws DaoException;
    public Ordonnance findById(Long identity) throws DaoException;
    public Ordonnance save(Ordonnance newElement) throws DaoException;
    public void update(Ordonnance newValuesElement) throws DaoException;
    public void delete(Ordonnance element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
