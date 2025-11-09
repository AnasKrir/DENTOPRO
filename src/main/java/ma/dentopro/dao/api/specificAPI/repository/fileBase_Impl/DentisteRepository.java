package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.DentisteRepo;
import ma.dentopro.model.Dentiste;
import ma.dentopro.utils.FileDatabase;
import ma.dentopro.model.enums.Specialite;

import java.util.ArrayList;
import java.util.List;

public class DentisteRepository implements DentisteRepo {
    private static final String FILE_PATH = "myFileBase/dentistes.txt";
    private static final String HEADER = "IdDentiste-Nom-Prenom-Specialité"; // En-tête pour actes.txt
    private FileDatabase fileDatabase;

    public DentisteRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Dentiste> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Dentiste> dentistes = new ArrayList<>();
        for (String line : lines) {
            dentistes.add(mapToDentiste(line));
        }
        return dentistes;
    }

    @Override
    public Dentiste findById(Long identity) throws DaoException {
        List<Dentiste> dentistes = findAll();
        for (Dentiste dentiste : dentistes) {
            if (dentiste.getId().equals(identity)) {
                return dentiste;
            }
        }
        return null;
    }

    @Override
    public Dentiste save(Dentiste newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setId(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Dentiste newValuesElement) throws DaoException {
        List<Dentiste> dentistes = findAll();
        for (int i = 0; i < dentistes.size(); i++) {
            if (dentistes.get(i).getId().equals(newValuesElement.getId())) {
                dentistes.set(i, newValuesElement);
                break;
            }
        }
        saveAll(dentistes);
    }

    @Override
    public void delete(Dentiste element) throws DaoException {
        List<Dentiste> dentistes = findAll();
        dentistes.removeIf(d -> d.getId().equals(element.getId()));
        saveAll(dentistes);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Dentiste> dentistes = findAll();
        dentistes.removeIf(d -> d.getId().equals(identity));
        saveAll(dentistes);
    }

    private Dentiste mapToDentiste(String line) {
        String[] parts = line.split("-");
        Dentiste dentiste = new Dentiste();
        dentiste.setId(Long.parseLong(parts[0]));
        dentiste.setNom(parts[1]);
        dentiste.setPrenom(parts[2]);
        dentiste.setSpecialite(Specialite.valueOf(parts[3]));
        return dentiste;
    }

    private String mapToLine(Dentiste dentiste) {
        return dentiste.getId() + "-" +
                dentiste.getNom() + "-" +
                dentiste.getPrenom() + "-" +
                dentiste.getSpecialite();
    }

    private void saveAll(List<Dentiste> dentistes) throws DaoException {
        fileDatabase.clear();
        for (Dentiste dentiste : dentistes) {
            fileDatabase.save(mapToLine(dentiste));
        }
    }
}