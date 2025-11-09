package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.PrescriptionDeMedicament;

import java.util.List;

public interface PrescriptionDeMedicamentRepo {

    public List<PrescriptionDeMedicament> findAll() throws DaoException;
    public PrescriptionDeMedicament findById(Long identity) throws DaoException;
    public PrescriptionDeMedicament save(PrescriptionDeMedicament newElement) throws DaoException;
    public void update(PrescriptionDeMedicament newValuesElement) throws DaoException;
    public void delete(PrescriptionDeMedicament element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
