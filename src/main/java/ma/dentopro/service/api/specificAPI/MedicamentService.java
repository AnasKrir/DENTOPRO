package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Medicament;

import java.util.List;

public interface MedicamentService {

    Medicament createMedicament(Medicament medicament) throws DaoException;
    Medicament updateMedicament(Medicament medicament) throws DaoException;
    void deleteMedicament(Long id) throws DaoException;
    Medicament getMedicamentById(Long id) throws DaoException;
    List<Medicament> getAllMedicaments() throws DaoException;
}
