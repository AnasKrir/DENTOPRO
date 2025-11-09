package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ActRepo;
import ma.dentopro.model.Acte;
import ma.dentopro.service.api.specificAPI.ActService;

import java.util.List;

public class ActServiceImpl implements ActService {

    private final ActRepo actRepo;

    public ActServiceImpl(ActRepo actRepo) {
        this.actRepo = actRepo;
    }

    @Override
    public Acte createActe(Acte acte) throws DaoException {
        return actRepo.save(acte);
    }

    @Override
    public Acte updateActe(Acte acte) throws DaoException {
        actRepo.update(acte);
        return acte;
    }

    @Override
    public void deleteActe(Long id) throws DaoException {
        actRepo.deleteById(id);
    }

    @Override
    public Acte getActeById(Long id) throws DaoException {
        return actRepo.findById(id);
    }

    @Override
    public List<Acte> getAllActes() throws DaoException {
        return actRepo.findAll();
    }
}
