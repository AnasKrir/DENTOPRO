package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.SecretaireRepo;
import ma.dentopro.model.Secretaire;
import ma.dentopro.service.api.specificAPI.SecretaireService;

import java.util.List;

public class SecretaireServiceImpl implements SecretaireService {

    private final SecretaireRepo secretaireRepo;

    public SecretaireServiceImpl(SecretaireRepo secretaireRepo) {
        this.secretaireRepo = secretaireRepo;
    }

    @Override
    public Secretaire createSecretaire(Secretaire secretaire) throws DaoException {
        return secretaireRepo.save(secretaire);
    }

    @Override
    public Secretaire updateSecretaire(Secretaire secretaire) throws DaoException {
        secretaireRepo.update(secretaire);
        return secretaire;
    }

    @Override
    public void deleteSecretaire(Long id) throws DaoException {
        secretaireRepo.deleteById(id);
    }

    @Override
    public Secretaire getSecretaireById(Long id) throws DaoException {
        return secretaireRepo.findById(id);
    }

    @Override
    public List<Secretaire> getAllSecretaires() throws DaoException {
        return secretaireRepo.findAll();
    }
}
