package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.DossierMedicale;

import java.util.List;

public interface DossierRepo {

    List<DossierMedicale> findAll() throws DaoException;
    DossierMedicale findById(Long identity) throws DaoException;
    DossierMedicale save(DossierMedicale newElement) throws DaoException;
    void update(DossierMedicale newValuesElement) throws DaoException;
    void delete(DossierMedicale element) throws DaoException;
    void deleteById(Long identity) throws DaoException;
}
