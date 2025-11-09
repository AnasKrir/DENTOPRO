package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ChargeRepo;
import ma.dentopro.model.Charge;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChargeRepository implements ChargeRepo {
    private static final String FILE_PATH = "myFileBase/charges.txt";
    private static final String HEADER = "IDCharge-Libelle"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public ChargeRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Charge> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Charge> charges = new ArrayList<>();
        for (String line : lines) {
            charges.add(mapToCharge(line));
        }
        return charges;
    }

    @Override
    public Charge findById(Long identity) throws DaoException {
        List<Charge> charges = findAll();
        for (Charge charge : charges) {
            if (charge.getIdCharge().equals(identity)) {
                return charge;
            }
        }
        return null;
    }

    @Override
    public Charge save(Charge newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdCharge(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Charge newValuesElement) throws DaoException {
        List<Charge> charges = findAll();
        for (int i = 0; i < charges.size(); i++) {
            if (charges.get(i).getIdCharge().equals(newValuesElement.getIdCharge())) {
                charges.set(i, newValuesElement);
                break;
            }
        }
        saveAll(charges);
    }

    @Override
    public void delete(Charge element) throws DaoException {
        List<Charge> charges = findAll();
        charges.removeIf(c -> c.getIdCharge().equals(element.getIdCharge()));
        saveAll(charges);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Charge> charges = findAll();
        charges.removeIf(c -> c.getIdCharge().equals(identity));
        saveAll(charges);
    }

    private Charge mapToCharge(String line) {
        String[] parts = line.split("-");
        Charge charge = new Charge();
        charge.setIdCharge(Long.parseLong(parts[0]));
        charge.setLibelle(parts[1]);
        return charge;
    }

    private String mapToLine(Charge charge) {
        return charge.getIdCharge() + "-" +
                charge.getLibelle();
    }

    private void saveAll(List<Charge> charges) throws DaoException {
        fileDatabase.clear();
        for (Charge charge : charges) {
            fileDatabase.save(mapToLine(charge));
        }
    }
}