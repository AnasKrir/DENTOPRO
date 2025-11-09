package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.OrdonnanceRepo;
import ma.dentopro.model.Ordonnance;
import ma.dentopro.service.api.specificAPI.OrdonnanceService;

import java.util.List;

public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final OrdonnanceRepo ordonnanceRepo;

    public OrdonnanceServiceImpl(OrdonnanceRepo ordonnanceRepo) {
        this.ordonnanceRepo = ordonnanceRepo;
    }

    @Override
    public Ordonnance createOrdonnance(Ordonnance ordonnance) throws DaoException {
        return ordonnanceRepo.save(ordonnance);
    }

    @Override
    public Ordonnance updateOrdonnance(Ordonnance ordonnance) throws DaoException {
        ordonnanceRepo.update(ordonnance);
        return ordonnance;
    }

    @Override
    public void deleteOrdonnance(Long id) throws DaoException {
        ordonnanceRepo.deleteById(id);
    }

    @Override
    public Ordonnance getOrdonnanceById(Long id) throws DaoException {
        return ordonnanceRepo.findById(id);
    }

    @Override
    public List<Ordonnance> getAllOrdonnances() throws DaoException {
        return ordonnanceRepo.findAll();
    }
}
