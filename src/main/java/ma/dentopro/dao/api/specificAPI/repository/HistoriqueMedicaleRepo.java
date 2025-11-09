package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.HistoriqueMedicale;

import java.util.List;

public interface HistoriqueMedicaleRepo {

    public List<HistoriqueMedicale> findAll() throws DaoException;
    public HistoriqueMedicale findById(Long identity) throws DaoException;
    public HistoriqueMedicale save(HistoriqueMedicale newElement) throws DaoException;
    public void update(HistoriqueMedicale newValuesElement) throws DaoException;
    public void delete(HistoriqueMedicale element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;

}
