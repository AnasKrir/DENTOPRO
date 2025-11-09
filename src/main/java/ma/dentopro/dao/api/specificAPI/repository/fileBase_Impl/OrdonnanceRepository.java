package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.OrdonnanceRepo;
import ma.dentopro.model.Ordonnance;
import ma.dentopro.utils.FileDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceRepository implements OrdonnanceRepo {
    private static final String FILE_PATH = "myFileBase/ordonnances.txt";
    private static final String HEADER = "IDOrdonnance-Date"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public OrdonnanceRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Ordonnance> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Ordonnance> ordonnances = new ArrayList<>();
        for (String line : lines) {
            ordonnances.add(mapToOrdonnance(line));
        }
        return ordonnances;
    }

    @Override
    public Ordonnance findById(Long identity) throws DaoException {
        List<Ordonnance> ordonnances = findAll();
        for (Ordonnance ordonnance : ordonnances) {
            if (ordonnance.getIdOrdonnance().equals(identity)) {
                return ordonnance;
            }
        }
        return null;
    }

    @Override
    public Ordonnance save(Ordonnance newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdOrdonnance(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Ordonnance newValuesElement) throws DaoException {
        List<Ordonnance> ordonnances = findAll();
        for (int i = 0; i < ordonnances.size(); i++) {
            if (ordonnances.get(i).getIdOrdonnance().equals(newValuesElement.getIdOrdonnance())) {
                ordonnances.set(i, newValuesElement);
                break;
            }
        }
        saveAll(ordonnances);
    }

    @Override
    public void delete(Ordonnance element) throws DaoException {
        List<Ordonnance> ordonnances = findAll();
        ordonnances.removeIf(o -> o.getIdOrdonnance().equals(element.getIdOrdonnance()));
        saveAll(ordonnances);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Ordonnance> ordonnances = findAll();
        ordonnances.removeIf(o -> o.getIdOrdonnance().equals(identity));
        saveAll(ordonnances);
    }

    private Ordonnance mapToOrdonnance(String line) {
        String[] parts = line.split("-");
        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setIdOrdonnance(Long.parseLong(parts[0]));
        ordonnance.setDate(LocalDate.parse(parts[1]));
        // Les objets Consultation et PrescriptionDeMedicament ne sont pas stockés dans le fichier
        return ordonnance;
    }

    private String mapToLine(Ordonnance ordonnance) {
        return ordonnance.getIdOrdonnance() + "-" +
                ordonnance.getDate();
    }
    private void saveAll(List<Ordonnance> ordonnances) throws DaoException {
        fileDatabase.clear();
        for (Ordonnance ordonnance : ordonnances) {
            fileDatabase.save(mapToLine(ordonnance));
        }
    }
}