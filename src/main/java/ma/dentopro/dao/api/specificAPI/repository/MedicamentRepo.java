package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Medicament;

import java.util.List;

public interface MedicamentRepo {

    public List<Medicament> findAll() throws DaoException;
    public Medicament findById(Long identity) throws DaoException;
    public Medicament save(Medicament newElement) throws DaoException;
    public void update(Medicament newValuesElement) throws DaoException;
    public void delete(Medicament element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
