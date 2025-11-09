package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.RendezVous;

import java.util.List;

public interface RendezVousService {

    RendezVous createRendezVous(RendezVous rendezVous) throws DaoException;
    RendezVous updateRendezVous(RendezVous rendezVous) throws DaoException;
    void deleteRendezVous(Long id) throws DaoException;
    RendezVous getRendezVousById(Long id) throws DaoException;
    List<RendezVous> getAllRendezVous() throws DaoException;
}
