package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.MedicamentRepo;
import ma.dentopro.model.Medicament;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicamentRepository implements MedicamentRepo {
    private static final String FILE_PATH = "myFileBase/medicaments.txt";
    private static final String HEADER = "IdMedicament-Nom-Description-Prix"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public MedicamentRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Medicament> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Medicament> medicaments = new ArrayList<>();
        for (String line : lines) {
            medicaments.add(mapToMedicament(line));
        }
        return medicaments;
    }

    @Override
    public Medicament findById(Long identity) throws DaoException {
        List<Medicament> medicaments = findAll();
        for (Medicament medicament : medicaments) {
            if (medicament.getIdMedicament().equals(identity)) {
                return medicament;
            }
        }
        return null;
    }

    @Override
    public Medicament save(Medicament newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdMedicament(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Medicament newValuesElement) throws DaoException {
        List<Medicament> medicaments = findAll();
        for (int i = 0; i < medicaments.size(); i++) {
            if (medicaments.get(i).getIdMedicament().equals(newValuesElement.getIdMedicament())) {
                medicaments.set(i, newValuesElement);
                break;
            }
        }
        saveAll(medicaments);
    }

    @Override
    public void delete(Medicament element) throws DaoException {
        List<Medicament> medicaments = findAll();
        medicaments.removeIf(m -> m.getIdMedicament().equals(element.getIdMedicament()));
        saveAll(medicaments);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Medicament> medicaments = findAll();
        medicaments.removeIf(m -> m.getIdMedicament().equals(identity));
        saveAll(medicaments);
    }

    private Medicament mapToMedicament(String line) {
        String[] parts = line.split("-");
        Medicament medicament = new Medicament();
        medicament.setIdMedicament(Long.parseLong(parts[0]));
        medicament.setNom(parts[1]);
        medicament.setDescription(parts[2]);
        medicament.setPrix(Double.parseDouble(parts[3]));
        // Les objets PrescriptionDeMedicament et HistoriqueMedicale ne sont pas stockés dans le fichier
        return medicament;
    }

    private String mapToLine(Medicament medicament) {
        return medicament.getIdMedicament() + "-" +
                medicament.getNom() + "-" +
                medicament.getDescription() + "-" +
                medicament.getPrix();
    }
    private void saveAll(List<Medicament> medicaments) throws DaoException {
        fileDatabase.clear();
        for (Medicament medicament : medicaments) {
            fileDatabase.save(mapToLine(medicament));
        }
    }
}