package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.MedicamentRepo;
import ma.dentopro.model.Medicament;
import ma.dentopro.service.api.specificAPI.MedicamentService;

import java.util.List;

public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentRepo medicamentRepo;

    public MedicamentServiceImpl(MedicamentRepo medicamentRepo) {
        this.medicamentRepo = medicamentRepo;
    }

    @Override
    public Medicament createMedicament(Medicament medicament) throws DaoException {
        return medicamentRepo.save(medicament);
    }

    @Override
    public Medicament updateMedicament(Medicament medicament) throws DaoException {
        medicamentRepo.update(medicament);
        return medicament;
    }

    @Override
    public void deleteMedicament(Long id) throws DaoException {
        medicamentRepo.deleteById(id);
    }

    @Override
    public Medicament getMedicamentById(Long id) throws DaoException {
        return medicamentRepo.findById(id);
    }

    @Override
    public List<Medicament> getAllMedicaments() throws DaoException {
        return medicamentRepo.findAll();
    }
}
