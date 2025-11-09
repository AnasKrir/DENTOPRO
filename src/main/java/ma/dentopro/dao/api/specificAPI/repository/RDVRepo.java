package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.RendezVous;

import java.util.List;

public interface RDVRepo {

    public List<RendezVous> findAll() throws DaoException;
    public RendezVous findById(Long identity) throws DaoException;
    public RendezVous save(RendezVous newElement) throws DaoException;
    public void update(RendezVous newValuesElement) throws DaoException;
    public void delete(RendezVous element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
