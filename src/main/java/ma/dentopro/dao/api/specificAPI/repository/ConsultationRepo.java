package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Consultation;

import java.util.List;

public interface ConsultationRepo {
    List<Consultation> findAll() throws DaoException;
    public Consultation findById(Long identity) throws DaoException;
    public Consultation save(Consultation newElement) throws DaoException;
    public void update(Consultation newValuesElement) throws DaoException;
    public void delete(Consultation element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
