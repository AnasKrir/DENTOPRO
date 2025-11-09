package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Facture;

import java.util.List;

public interface FactureRepo {
    public List<Facture> findAll() throws DaoException;
    public Facture findById(Long identity) throws DaoException;
    public Facture save(Facture newElement) throws DaoException;
    public void update(Facture newValuesElement) throws DaoException;
    public void delete(Facture element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
