package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.InterventionMedecinRepo;
import ma.dentopro.model.InterventionMedecin;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class InterventionMedecinRepository implements InterventionMedecinRepo {
    private static final String FILE_PATH = "myFileBase/interventions.txt";
    private static final String HEADER = "IDIntervention-NoteMedecin-N°Dent-PrixPatient"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public InterventionMedecinRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<InterventionMedecin> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<InterventionMedecin> interventions = new ArrayList<>();
        for (String line : lines) {
            interventions.add(mapToIntervention(line));
        }
        return interventions;
    }

    @Override
    public InterventionMedecin findById(Long identity) throws DaoException {
        List<InterventionMedecin> interventions = findAll();
        for (InterventionMedecin intervention : interventions) {
            if (intervention.getIdIntervention().equals(identity)) {
                return intervention;
            }
        }
        return null;
    }

    @Override
    public InterventionMedecin save(InterventionMedecin newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdIntervention(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(InterventionMedecin newValuesElement) throws DaoException {
        List<InterventionMedecin> interventions = findAll();
        for (int i = 0; i < interventions.size(); i++) {
            if (interventions.get(i).getIdIntervention().equals(newValuesElement.getIdIntervention())) {
                interventions.set(i, newValuesElement);
                break;
            }
        }
        saveAll(interventions);
    }

    @Override
    public void delete(InterventionMedecin element) throws DaoException {
        List<InterventionMedecin> interventions = findAll();
        interventions.removeIf(i -> i.getIdIntervention().equals(element.getIdIntervention()));
        saveAll(interventions);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<InterventionMedecin> interventions = findAll();
        interventions.removeIf(i -> i.getIdIntervention().equals(identity));
        saveAll(interventions);
    }

    private InterventionMedecin mapToIntervention(String line) {
        String[] parts = line.split("-");
        InterventionMedecin intervention = new InterventionMedecin();
        intervention.setIdIntervention(Long.parseLong(parts[0]));
        intervention.setNoteMedecin(parts[1]);
        intervention.setDent(Long.parseLong(parts[2]));
        intervention.setPrixPatient(Double.parseDouble(parts[3]));
        // Les objets Acte et Consultation ne sont pas stockés dans le fichier
        return intervention;
    }

    private String mapToLine(InterventionMedecin intervention) {
        return intervention.getIdIntervention() + "-" +
                intervention.getNoteMedecin() + "-" +
                intervention.getDent() + "-" +
                intervention.getPrixPatient();
    }
    private void saveAll(List<InterventionMedecin> interventions) throws DaoException {
        fileDatabase.clear();
        for (InterventionMedecin intervention : interventions) {
            fileDatabase.save(mapToLine(intervention));
        }
    }
}