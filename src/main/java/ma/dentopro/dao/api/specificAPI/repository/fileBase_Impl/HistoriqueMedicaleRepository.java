package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.HistoriqueMedicaleRepo;
import ma.dentopro.model.HistoriqueMedicale;
import ma.dentopro.model.enums.CategorieHistoriqueMedicale;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueMedicaleRepository implements HistoriqueMedicaleRepo {
    private static final String FILE_PATH = "myFileBase/historiques.txt";
    private static final String HEADER = "IDAntecedent-Libelle-Categorie"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public HistoriqueMedicaleRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<HistoriqueMedicale> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<HistoriqueMedicale> historiques = new ArrayList<>();
        for (String line : lines) {
            historiques.add(mapToHistorique(line));
        }
        return historiques;
    }

    @Override
    public HistoriqueMedicale findById(Long identity) throws DaoException {
        List<HistoriqueMedicale> historiques = findAll();
        for (HistoriqueMedicale historique : historiques) {
            if (historique.getIdAntecedent().equals(identity)) {
                return historique;
            }
        }
        return null;
    }

    @Override
    public HistoriqueMedicale save(HistoriqueMedicale newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdAntecedent(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(HistoriqueMedicale newValuesElement) throws DaoException {
        List<HistoriqueMedicale> historiques = findAll();
        for (int i = 0; i < historiques.size(); i++) {
            if (historiques.get(i).getIdAntecedent().equals(newValuesElement.getIdAntecedent())) {
                historiques.set(i, newValuesElement);
                break;
            }
        }
        saveAll(historiques);
    }

    @Override
    public void delete(HistoriqueMedicale element) throws DaoException {
        List<HistoriqueMedicale> historiques = findAll();
        historiques.removeIf(h -> h.getIdAntecedent().equals(element.getIdAntecedent()));
        saveAll(historiques);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<HistoriqueMedicale> historiques = findAll();
        historiques.removeIf(h -> h.getIdAntecedent().equals(identity));
        saveAll(historiques);
    }

    private HistoriqueMedicale mapToHistorique(String line) {
        String[] parts = line.split("-");
        HistoriqueMedicale historique = new HistoriqueMedicale();
        historique.setIdAntecedent(Long.parseLong(parts[0]));
        historique.setLibelle(parts[1]);
        historique.setCategorie(CategorieHistoriqueMedicale.valueOf(parts[2]));
        // Les patients ne sont pas stockés dans le fichier
        return historique;
    }

    private String mapToLine(HistoriqueMedicale historique) {
        return historique.getIdAntecedent() + "-" +
                historique.getLibelle() + "-" +
                historique.getCategorie();
    }
    private void saveAll(List<HistoriqueMedicale> historiques) throws DaoException {
        fileDatabase.clear();
        for (HistoriqueMedicale historique : historiques) {
            fileDatabase.save(mapToLine(historique));
        }
    }
}