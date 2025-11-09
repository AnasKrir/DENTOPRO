package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RDVRepo;
import ma.dentopro.model.RendezVous;
import ma.dentopro.model.enums.TypeRDV;
import ma.dentopro.utils.FileDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousRepository implements RDVRepo {
    private static final String FILE_PATH = "myFileBase/RDVaApprouve.txt";
    private static final String HEADER = "IDRDV-TypeRDV-DateRDV-Motif-Temps"; // En-tête pour actes.txt
    private FileDatabase fileDatabase;

    public RendezVousRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<RendezVous> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<RendezVous> rendezVousList = new ArrayList<>();
        for (String line : lines) {
            rendezVousList.add(mapToRendezVous(line));
        }
        return rendezVousList;
    }

    @Override
    public RendezVous findById(Long identity) throws DaoException {
        List<RendezVous> rendezVousList = findAll();
        for (RendezVous rdv : rendezVousList) {
            if (rdv.getIdRDV().equals(identity)) {
                return rdv;
            }
        }
        return null;
    }

    @Override
    public RendezVous save(RendezVous newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdRDV(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(RendezVous newValuesElement) throws DaoException {
        List<RendezVous> rendezVousList = findAll();
        for (int i = 0; i < rendezVousList.size(); i++) {
            if (rendezVousList.get(i).getIdRDV().equals(newValuesElement.getIdRDV())) {
                rendezVousList.set(i, newValuesElement);
                break;
            }
        }
        saveAll(rendezVousList);
    }

    @Override
    public void delete(RendezVous element) throws DaoException {
        List<RendezVous> rendezVousList = findAll();
        rendezVousList.removeIf(rdv -> rdv.getIdRDV().equals(element.getIdRDV()));
        saveAll(rendezVousList);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<RendezVous> rendezVousList = findAll();
        rendezVousList.removeIf(rdv -> rdv.getIdRDV().equals(identity));
        saveAll(rendezVousList);
    }

    private RendezVous mapToRendezVous(String line) {
        String[] parts = line.split("-");
        RendezVous rdv = new RendezVous();
        rdv.setIdRDV(Long.parseLong(parts[0]));
        rdv.setTypeRDV(TypeRDV.valueOf(parts[1]));
        rdv.setDateRDV(LocalDate.parse(parts[2]));
        rdv.setMotif(parts[3]);
        rdv.setTemps(LocalTime.parse(parts[4]));
        return rdv;
    }

    private String mapToLine(RendezVous rdv) {
        return rdv.getIdRDV() + "-" +
                rdv.getTypeRDV() + "-" +
                rdv.getDateRDV() + "-" +
                rdv.getMotif() + "-" +
                rdv.getTemps();
    }

    private void saveAll(List<RendezVous> rendezVousList) throws DaoException {
        fileDatabase.clear();
        for (RendezVous rdv : rendezVousList) {
            fileDatabase.save(mapToLine(rdv));
        }
    }
}