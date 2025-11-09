package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.InterventionMedecin;

import java.util.List;

public interface InterventionMedecinRepo {

    public List<InterventionMedecin> findAll() throws DaoException;
    public InterventionMedecin findById(Long identity) throws DaoException;
    public InterventionMedecin save(InterventionMedecin newElement) throws DaoException;
    public void update(InterventionMedecin newValuesElement) throws DaoException;
    public void delete(InterventionMedecin element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;

}
