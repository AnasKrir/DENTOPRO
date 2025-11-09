package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.CabinetRepo;
import ma.dentopro.model.Cabinet;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class CabinetRepository implements CabinetRepo {
    private static final String FILE_PATH = "myFileBase/cabinets.txt";
    private static final String HEADER = "IDCabinet-Nom-Adresse-Logo-Email-Tel-Telephone1-Telephone2"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public CabinetRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Cabinet> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Cabinet> cabinets = new ArrayList<>();
        for (String line : lines) {
            cabinets.add(mapToCabinet(line));
        }
        return cabinets;
    }

    @Override
    public Cabinet findById(Long identity) throws DaoException {
        List<Cabinet> cabinets = findAll();
        for (Cabinet cabinet : cabinets) {
            if (cabinet.getIdCabinet().equals(identity)) {
                return cabinet;
            }
        }
        return null;
    }

    @Override
    public Cabinet save(Cabinet newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdCabinet(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Cabinet newValuesElement) throws DaoException {
        List<Cabinet> cabinets = findAll();
        for (int i = 0; i < cabinets.size(); i++) {
            if (cabinets.get(i).getIdCabinet().equals(newValuesElement.getIdCabinet())) {
                cabinets.set(i, newValuesElement);
                break;
            }
        }
        saveAll(cabinets);
    }

    @Override
    public void delete(Cabinet element) throws DaoException {
        List<Cabinet> cabinets = findAll();
        cabinets.removeIf(c -> c.getIdCabinet().equals(element.getIdCabinet()));
        saveAll(cabinets);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Cabinet> cabinets = findAll();
        cabinets.removeIf(c -> c.getIdCabinet().equals(identity));
        saveAll(cabinets);
    }

    private Cabinet mapToCabinet(String line) {
        String[] parts = line.split("-");
        Cabinet cabinet = new Cabinet();
        cabinet.setIdCabinet(Long.parseLong(parts[0]));
        cabinet.setNom(parts[1]);
        cabinet.setAdresse(parts[2]);
        cabinet.setLogo(parts[3]);
        cabinet.setEmail(parts[4]);
        cabinet.setTel(parts[5]);
        cabinet.setTelephone1(parts[6]);
        cabinet.setTelephone2(parts[7]);
        // Les objets Caisse et Staff ne sont pas stockés dans le fichier
        return cabinet;
    }

    private String mapToLine(Cabinet cabinet) {
        return cabinet.getIdCabinet() + "-" +
                cabinet.getNom() + "-" +
                cabinet.getAdresse() + "-" +
                cabinet.getLogo() + "-" +
                cabinet.getEmail() + "-" +
                cabinet.getTel() + "-" +
                cabinet.getTelephone1() + "-" +
                cabinet.getTelephone2();
    }

    private void saveAll(List<Cabinet> cabinets) throws DaoException {
        fileDatabase.clear();
        for (Cabinet cabinet : cabinets) {
            fileDatabase.save(mapToLine(cabinet));
        }
    }
}