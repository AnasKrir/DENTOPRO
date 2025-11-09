package ma.dentopro.service.api.specificAPI.service_Impl;


import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RDVRepo;
import ma.dentopro.model.RendezVous;
import ma.dentopro.service.api.specificAPI.RendezVousService;

import java.util.List;

public class RendezVousServiceImpl implements RendezVousService {

    private final RDVRepo rendezVousRepo;

    public RendezVousServiceImpl(RDVRepo rendezVousRepo) {
        this.rendezVousRepo = rendezVousRepo;
    }

    @Override
    public RendezVous createRendezVous(RendezVous rendezVous) throws DaoException {
        return rendezVousRepo.save(rendezVous);
    }

    @Override
    public RendezVous updateRendezVous(RendezVous rendezVous) throws DaoException {
        rendezVousRepo.update(rendezVous);
        return rendezVous;
    }

    @Override
    public void deleteRendezVous(Long id) throws DaoException {
        rendezVousRepo.deleteById(id);
    }

    @Override
    public RendezVous getRendezVousById(Long id) throws DaoException {
        return rendezVousRepo.findById(id);
    }

    @Override
    public List<RendezVous> getAllRendezVous() throws DaoException {
        return rendezVousRepo.findAll();
    }


}
