package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.FactureRepo;
import ma.dentopro.model.Facture;
import ma.dentopro.service.api.specificAPI.FactureService;

import java.util.List;

public class FactureServiceImpl implements FactureService {

    private final FactureRepo factureRepo;

    public FactureServiceImpl(FactureRepo factureRepo) {
        this.factureRepo = factureRepo;
    }

    @Override
    public Facture createFacture(Facture facture) throws DaoException {
        return factureRepo.save(facture);
    }

    @Override
    public Facture updateFacture(Facture facture) throws DaoException {
        factureRepo.update(facture);
        return facture;
    }

    @Override
    public void deleteFacture(Long id) throws DaoException {
        factureRepo.deleteById(id);
    }

    @Override
    public Facture getFactureById(Long id) throws DaoException {
        return factureRepo.findById(id);
    }

    @Override
    public List<Facture> getAllFactures() throws DaoException {
        return factureRepo.findAll();
    }
}
