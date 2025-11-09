package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.FactureRepo;
import ma.dentopro.model.Facture;
import ma.dentopro.model.enums.TypePaiement;
import ma.dentopro.utils.FileDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FactureRepository implements FactureRepo {
    private static final String FILE_PATH = "myFileBase/factures.txt";
    private static final String HEADER = "IDFacture-MontantTotal-DateFacturation-TypePaiement-MontantRestant-MontantPaye"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public FactureRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Facture> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Facture> factures = new ArrayList<>();
        for (String line : lines) {
            factures.add(mapToFacture(line));
        }
        return factures;
    }

    @Override
    public Facture findById(Long identity) throws DaoException {
        List<Facture> factures = findAll();
        for (Facture facture : factures) {
            if (facture.getIdFacture().equals(identity)) {
                return facture;
            }
        }
        return null;
    }

    @Override
    public Facture save(Facture newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdFacture(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Facture newValuesElement) throws DaoException {
        List<Facture> factures = findAll();
        for (int i = 0; i < factures.size(); i++) {
            if (factures.get(i).getIdFacture().equals(newValuesElement.getIdFacture())) {
                factures.set(i, newValuesElement);
                break;
            }
        }
        saveAll(factures);
    }

    @Override
    public void delete(Facture element) throws DaoException {
        List<Facture> factures = findAll();
        factures.removeIf(f -> f.getIdFacture().equals(element.getIdFacture()));
        saveAll(factures);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Facture> factures = findAll();
        factures.removeIf(f -> f.getIdFacture().equals(identity));
        saveAll(factures);
    }

    private Facture mapToFacture(String line) {
        String[] parts = line.split("-");
        Facture facture = new Facture();
        facture.setIdFacture(Long.parseLong(parts[0]));
        facture.setMontantTotal(Double.parseDouble(parts[1]));
        facture.setDateFacturation(LocalDate.parse(parts[2]));
        facture.setTypePaiement(TypePaiement.valueOf(parts[3]));
        facture.setMontantRestant(Double.parseDouble(parts[4]));
        facture.setMontantPaye(Double.parseDouble(parts[5]));
        // Les objets Consultation et SituationFinanciere ne sont pas stockés dans le fichier
        return facture;
    }

    private String mapToLine(Facture facture) {
        return facture.getIdFacture() + "-" +
                facture.getMontantTotal() + "-" +
                facture.getDateFacturation() + "-" +
                facture.getTypePaiement() + "-" +
                facture.getMontantRestant() + "-" +
                facture.getMontantPaye();
    }
    private void saveAll(List<Facture> factures) throws DaoException {
        fileDatabase.clear();
        for (Facture facture : factures) {
            fileDatabase.save(mapToLine(facture));
        }
    }
}