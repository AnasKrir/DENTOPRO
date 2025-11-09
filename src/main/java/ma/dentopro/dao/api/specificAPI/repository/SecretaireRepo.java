package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Secretaire;

import java.util.List;

public interface SecretaireRepo {

    List<Secretaire> findAll() throws DaoException;
    Secretaire findById(Long identity) throws DaoException;
    Secretaire save(Secretaire newElement) throws DaoException;
    void update(Secretaire newValuesElement) throws DaoException;
    void delete(Secretaire element) throws DaoException;
    void deleteById(Long identity) throws DaoException;
}
