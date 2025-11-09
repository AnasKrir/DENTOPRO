package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.SituationFinanciereRepo;
import ma.dentopro.model.SituationFinanciere;
import ma.dentopro.service.api.specificAPI.SituationFinanciereService;

import java.util.List;

public class SituationFinanciereServiceImpl implements SituationFinanciereService {

    private final SituationFinanciereRepo situationFinanciereRepo;

    public SituationFinanciereServiceImpl(SituationFinanciereRepo situationFinanciereRepo) {
        this.situationFinanciereRepo = situationFinanciereRepo;
    }

    @Override
    public SituationFinanciere createSituationFinanciere(SituationFinanciere situationFinanciere) throws DaoException {
        return situationFinanciereRepo.save(situationFinanciere);
    }

    @Override
    public SituationFinanciere updateSituationFinanciere(SituationFinanciere situationFinanciere) throws DaoException {
        situationFinanciereRepo.update(situationFinanciere);
        return situationFinanciere;
    }

    @Override
    public void deleteSituationFinanciere(Long id) throws DaoException {
        situationFinanciereRepo.deleteById(id);
    }

    @Override
    public SituationFinanciere getSituationFinanciereById(Long id) throws DaoException {
        return situationFinanciereRepo.findById(id);
    }

    @Override
    public List<SituationFinanciere> getAllSituationFinancieres() throws DaoException {
        return situationFinanciereRepo.findAll();
    }
}
