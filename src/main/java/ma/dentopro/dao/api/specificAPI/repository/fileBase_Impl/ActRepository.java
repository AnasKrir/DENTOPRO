package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ActRepo;
import ma.dentopro.model.Acte;
import ma.dentopro.model.enums.CategorieActe;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActRepository implements ActRepo {
    private static final String FILE_PATH = "myFileBase/actes.txt";
    private static final String HEADER = "IDActe-Libelle-PrixDeBase-Categorie"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public ActRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Acte> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Acte> actes = new ArrayList<>();
        for (String line : lines) {
            actes.add(mapToActe(line));
        }
        return actes;
    }

    @Override
    public Acte findById(Long identity) throws DaoException {
        List<Acte> actes = findAll();
        for (Acte acte : actes) {
            if (acte.getIdActe().equals(identity)) {
                return acte;
            }
        }
        return null;
    }

    @Override
    public Acte save(Acte newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdActe(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Acte newValuesElement) throws DaoException {
        List<Acte> actes = findAll();
        for (int i = 0; i < actes.size(); i++) {
            if (actes.get(i).getIdActe().equals(newValuesElement.getIdActe())) {
                actes.set(i, newValuesElement);
                break;
            }
        }
        saveAll(actes);
    }

    @Override
    public void delete(Acte element) throws DaoException {
        List<Acte> actes = findAll();
        actes.removeIf(a -> a.getIdActe().equals(element.getIdActe()));
        saveAll(actes);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Acte> actes = findAll();
        actes.removeIf(a -> a.getIdActe().equals(identity));
        saveAll(actes);
    }

    private Acte mapToActe(String line) {
        String[] parts = line.split("-");
        Acte acte = new Acte();
        acte.setIdActe(Long.parseLong(parts[0]));
        acte.setLibelle(parts[1]);
        acte.setPrixDeBase(Double.parseDouble(parts[2]));
        acte.setCategorie(CategorieActe.valueOf(parts[3]));
        // Les interventions ne sont pas stockées dans le fichier.
        return acte;
    }

    private String mapToLine(Acte acte) {
        return acte.getIdActe() + "-" +
                acte.getLibelle() + "-" +
                acte.getPrixDeBase() + "-" +
                acte.getCategorie();
    }

    private void saveAll(List<Acte> actes) throws DaoException {
        fileDatabase.clear();
        for (Acte acte : actes) {
            fileDatabase.save(mapToLine(acte));
        }
    }
}