package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Secretaire;

import java.util.List;

public interface SecretaireService {

    Secretaire createSecretaire(Secretaire secretaire) throws DaoException;
    Secretaire updateSecretaire(Secretaire secretaire) throws DaoException;
    void deleteSecretaire(Long id) throws DaoException;
    Secretaire getSecretaireById(Long id) throws DaoException;
    List<Secretaire> getAllSecretaires() throws DaoException;
}
