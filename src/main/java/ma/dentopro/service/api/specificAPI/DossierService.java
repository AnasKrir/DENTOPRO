package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.DossierMedicale;

import java.util.List;

public interface DossierService {

    DossierMedicale createDossier(DossierMedicale dossier) throws DaoException;
    DossierMedicale updateDossier(DossierMedicale dossier) throws DaoException;
    void deleteDossier(Long id) throws DaoException;
    DossierMedicale getDossierById(Long id) throws DaoException;
    List<DossierMedicale> getAllDossiers() throws DaoException;
}
