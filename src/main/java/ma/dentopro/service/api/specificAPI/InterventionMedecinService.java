package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.InterventionMedecin;

import java.util.List;

public interface InterventionMedecinService {

    InterventionMedecin createInterventionMedecin(InterventionMedecin interventionMedecin) throws DaoException;
    InterventionMedecin updateInterventionMedecin(InterventionMedecin interventionMedecin) throws DaoException;
    void deleteInterventionMedecin(Long id) throws DaoException;
    InterventionMedecin getInterventionMedecinById(Long id) throws DaoException;
    List<InterventionMedecin> getAllInterventionMedecins() throws DaoException;
}
