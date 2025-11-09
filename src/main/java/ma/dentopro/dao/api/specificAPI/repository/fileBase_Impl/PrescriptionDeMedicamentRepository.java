package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.PrescriptionDeMedicamentRepo;
import ma.dentopro.model.PrescriptionDeMedicament;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionDeMedicamentRepository implements PrescriptionDeMedicamentRepo {
    private static final String FILE_PATH = "myFileBase/prescriptions.txt";
    private static final String HEADER = "IDPrescription-UniteMinAPrendre-UniteMaxAPrendre-ContraintesAlimentation-ContraintesTemps"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public PrescriptionDeMedicamentRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<PrescriptionDeMedicament> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<PrescriptionDeMedicament> prescriptions = new ArrayList<>();
        for (String line : lines) {
            prescriptions.add(mapToPrescription(line));
        }
        return prescriptions;
    }

    @Override
    public PrescriptionDeMedicament findById(Long identity) throws DaoException {
        List<PrescriptionDeMedicament> prescriptions = findAll();
        for (PrescriptionDeMedicament prescription : prescriptions) {
            if (prescription.getIdPrescription().equals(identity)) {
                return prescription;
            }
        }
        return null;
    }

    @Override
    public PrescriptionDeMedicament save(PrescriptionDeMedicament newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdPrescription(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(PrescriptionDeMedicament newValuesElement) throws DaoException {
        List<PrescriptionDeMedicament> prescriptions = findAll();
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getIdPrescription().equals(newValuesElement.getIdPrescription())) {
                prescriptions.set(i, newValuesElement);
                break;
            }
        }
        saveAll(prescriptions);
    }

    @Override
    public void delete(PrescriptionDeMedicament element) throws DaoException {
        List<PrescriptionDeMedicament> prescriptions = findAll();
        prescriptions.removeIf(p -> p.getIdPrescription().equals(element.getIdPrescription()));
        saveAll(prescriptions);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<PrescriptionDeMedicament> prescriptions = findAll();
        prescriptions.removeIf(p -> p.getIdPrescription().equals(identity));
        saveAll(prescriptions);
    }

    private PrescriptionDeMedicament mapToPrescription(String line) {
        String[] parts = line.split("-");
        PrescriptionDeMedicament prescription = new PrescriptionDeMedicament();
        prescription.setIdPrescription(Long.parseLong(parts[0]));
        prescription.setUnitesMinAPrendre(Integer.parseInt(parts[1]));
        prescription.setUnitesMaxAPrendre(Integer.parseInt(parts[2]));
        prescription.setContraintesAlimentation(parts[3]);
        prescription.setContraintesTemps(parts[4]);
        // Les objets Ordonnance et Medicament ne sont pas stockés dans le fichier
        return prescription;
    }

    private String mapToLine(PrescriptionDeMedicament prescription) {
        return prescription.getIdPrescription() + "-" +
                prescription.getUnitesMinAPrendre() + "-" +
                prescription.getUnitesMaxAPrendre() + "-" +
                prescription.getContraintesAlimentation() + "-" +
                prescription.getContraintesTemps();
    }

    private void saveAll(List<PrescriptionDeMedicament> prescriptions) throws DaoException {
        fileDatabase.clear();
        for (PrescriptionDeMedicament prescription : prescriptions) {
            fileDatabase.save(mapToLine(prescription));
        }
    }
}