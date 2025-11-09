package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.DossierRepo;
import ma.dentopro.model.DossierMedicale;
import ma.dentopro.service.api.specificAPI.DossierService;

import java.util.List;

public class DossierServiceImpl implements DossierService {

    private final DossierRepo dossierRepo;

    public DossierServiceImpl(DossierRepo dossierRepo) {
        this.dossierRepo = dossierRepo;
    }

    @Override
    public DossierMedicale createDossier(DossierMedicale dossier) throws DaoException {
        return dossierRepo.save(dossier);
    }

    @Override
    public DossierMedicale updateDossier(DossierMedicale dossier) throws DaoException {
        dossierRepo.update(dossier);
        return dossier;
    }

    @Override
    public void deleteDossier(Long id) throws DaoException {
        dossierRepo.deleteById(id);
    }

    @Override
    public DossierMedicale getDossierById(Long id) throws DaoException {
        return dossierRepo.findById(id);
    }

    @Override
    public List<DossierMedicale> getAllDossiers() throws DaoException {
        return dossierRepo.findAll();
    }
}
