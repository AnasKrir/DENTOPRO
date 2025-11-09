package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.InterventionMedecinRepo;
import ma.dentopro.model.InterventionMedecin;
import ma.dentopro.service.api.specificAPI.InterventionMedecinService;

import java.util.List;

public class InterventionMedecinServiceImpl implements InterventionMedecinService {

    private final InterventionMedecinRepo interventionMedecinRepo;

    public InterventionMedecinServiceImpl(InterventionMedecinRepo interventionMedecinRepo) {
        this.interventionMedecinRepo = interventionMedecinRepo;
    }

    @Override
    public InterventionMedecin createInterventionMedecin(InterventionMedecin interventionMedecin) throws DaoException {
        return interventionMedecinRepo.save(interventionMedecin);
    }

    @Override
    public InterventionMedecin updateInterventionMedecin(InterventionMedecin interventionMedecin) throws DaoException {
        interventionMedecinRepo.update(interventionMedecin);
        return interventionMedecin;
    }

    @Override
    public void deleteInterventionMedecin(Long id) throws DaoException {
        interventionMedecinRepo.deleteById(id);
    }

    @Override
    public InterventionMedecin getInterventionMedecinById(Long id) throws DaoException {
        return interventionMedecinRepo.findById(id);
    }

    @Override
    public List<InterventionMedecin> getAllInterventionMedecins() throws DaoException {
        return interventionMedecinRepo.findAll();
    }
}
