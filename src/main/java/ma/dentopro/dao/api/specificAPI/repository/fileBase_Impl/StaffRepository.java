package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.StaffRepo;
import ma.dentopro.model.Staff;
import ma.dentopro.model.enums.StatusEmploye;
import ma.dentopro.utils.FileDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository implements StaffRepo {
    private static final String FILE_PATH = "myFileBase/staff.txt";
    private static final String HEADER = "ID-Nom-StatusActuel-SalaireDeBase-DateRetourConge"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public StaffRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Staff> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Staff> staffList = new ArrayList<>();
        for (String line : lines) {
            staffList.add(mapToStaff(line));
        }
        return staffList;
    }

    @Override
    public Staff findById(Long identity) throws DaoException {
        List<Staff> staffList = findAll();
        for (Staff staff : staffList) {
            if (staff.getId().equals(identity)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public Staff save(Staff newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setId(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Staff newValuesElement) throws DaoException {
        List<Staff> staffList = findAll();
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId().equals(newValuesElement.getId())) {
                staffList.set(i, newValuesElement);
                break;
            }
        }
        saveAll(staffList);
    }

    @Override
    public void delete(Staff element) throws DaoException {
        List<Staff> staffList = findAll();
        staffList.removeIf(s -> s.getId().equals(element.getId()));
        saveAll(staffList);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Staff> staffList = findAll();
        staffList.removeIf(s -> s.getId().equals(identity));
        saveAll(staffList);
    }

    private Staff mapToStaff(String line) {
        String[] parts = line.split("-");
        Staff staff = new Staff();
        staff.setId(Long.parseLong(parts[0]));
        staff.setNom(parts[1]);
        staff.setCabinetDeTravail(null); // À gérer séparément
        staff.setStatusActuel(StatusEmploye.valueOf(parts[2]));
        staff.setSalaireDebase(Double.parseDouble(parts[3]));
        staff.setDateRetourConge(LocalDate.parse(parts[4]));
        // La disponibilité n'est pas stockée dans le fichier
        return staff;
    }

    private String mapToLine(Staff staff) {
        return staff.getId() + "-" +
                staff.getNom() + "-" +
                staff.getStatusActuel() + "-" +
                staff.getSalaireDebase() + "-" +
                staff.getDateRetourConge();
    }
    private void saveAll(List<Staff> staffList) throws DaoException {
        fileDatabase.clear();
        for (Staff staff : staffList) {
            fileDatabase.save(mapToLine(staff));
        }
    }
}