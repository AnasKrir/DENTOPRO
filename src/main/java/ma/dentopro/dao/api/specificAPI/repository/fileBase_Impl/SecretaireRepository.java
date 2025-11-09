package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.SecretaireRepo;
import ma.dentopro.model.Secretaire;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class SecretaireRepository implements SecretaireRepo {
    private static final String FILE_PATH = "myFileBase/secretaires.txt";
    private static final String HEADER = "ID-Nom-Prenom-Email-Telephone"; // En-tête pour secretaires.txt
    private final FileDatabase fileDatabase;

    public SecretaireRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Secretaire> findAll() throws DaoException {
        List<String> lines = fileDatabase.load(); // Ignore automatiquement l'en-tête
        List<Secretaire> secretaires = new ArrayList<>();
        for (String line : lines) {
            secretaires.add(mapToSecretaire(line));
        }
        return secretaires;
    }

    @Override
    public Secretaire findById(Long identity) throws DaoException {
        List<Secretaire> secretaires = findAll();
        for (Secretaire secretaire : secretaires) {
            if (secretaire.getId().equals(identity)) {
                return secretaire;
            }
        }
        return null;
    }

    @Override
    public Secretaire save(Secretaire newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setId(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Secretaire newValuesElement) throws DaoException {
        List<Secretaire> secretaires = findAll();
        for (int i = 0; i < secretaires.size(); i++) {
            if (secretaires.get(i).getId().equals(newValuesElement.getId())) {
                secretaires.set(i, newValuesElement);
                break;
            }
        }
        saveAll(secretaires);
    }

    @Override
    public void delete(Secretaire element) throws DaoException {
        List<Secretaire> secretaires = findAll();
        secretaires.removeIf(s -> s.getId().equals(element.getId()));
        saveAll(secretaires);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Secretaire> secretaires = findAll();
        secretaires.removeIf(s -> s.getId().equals(identity));
        saveAll(secretaires);
    }

    private Secretaire mapToSecretaire(String line) {
        String[] parts = line.split("-");
        Secretaire secretaire = new Secretaire();
        secretaire.setId(Long.parseLong(parts[0]));
        secretaire.setNom(parts[1]);
        secretaire.setPrenom(parts[2]);
        secretaire.setEmail(parts[3]);
        secretaire.setTelephone(parts[4]);
        return secretaire;
    }

    private String mapToLine(Secretaire secretaire) {
        return secretaire.getId() + "-" +
                secretaire.getNom() + "-" +
                secretaire.getPrenom() + "-" +
                secretaire.getEmail() + "-" +
                secretaire.getTelephone();
    }

    private void saveAll(List<Secretaire> secretaires) throws DaoException {
        fileDatabase.clear();
        for (Secretaire secretaire : secretaires) {
            fileDatabase.save(mapToLine(secretaire));
        }
    }
}