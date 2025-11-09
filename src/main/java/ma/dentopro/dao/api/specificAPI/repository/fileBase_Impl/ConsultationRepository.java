package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ConsultationRepo;
import ma.dentopro.model.Consultation;
import ma.dentopro.model.enums.TypeConsultation;
import ma.dentopro.utils.FileDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationRepository implements ConsultationRepo {
    private static final String FILE_PATH = "myFileBase/consultations.txt";
    private static final String HEADER = "IDConsultation-TypeConsultation-DateConsultation"; // En-tête pour actes.txt
    private FileDatabase fileDatabase;

    public ConsultationRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Consultation> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Consultation> consultations = new ArrayList<>();
        for (String line : lines) {
            consultations.add(mapToConsultation(line));
        }
        return consultations;
    }

    @Override
    public Consultation findById(Long identity) throws DaoException {
        List<Consultation> consultations = findAll();
        for (Consultation consultation : consultations) {
            if (consultation.getIdConsultation().equals(identity)) {
                return consultation;
            }
        }
        return null;
    }

    @Override
    public Consultation save(Consultation newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdConsultation(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Consultation newValuesElement) throws DaoException {
        List<Consultation> consultations = findAll();
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getIdConsultation().equals(newValuesElement.getIdConsultation())) {
                consultations.set(i, newValuesElement);
                break;
            }
        }
        saveAll(consultations);
    }

    @Override
    public void delete(Consultation element) throws DaoException {
        List<Consultation> consultations = findAll();
        consultations.removeIf(c -> c.getIdConsultation().equals(element.getIdConsultation()));
        saveAll(consultations);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Consultation> consultations = findAll();
        consultations.removeIf(c -> c.getIdConsultation().equals(identity));
        saveAll(consultations);
    }

    private Consultation mapToConsultation(String line) {
        String[] parts = line.split("-");
        Consultation consultation = new Consultation();
        consultation.setIdConsultation(Long.parseLong(parts[0]));
        consultation.setTypeConsultation(TypeConsultation.valueOf(parts[1]));
        consultation.setDateConsultation(LocalDate.parse(parts[2]));
        // Les objets Facture, Ordonnance, et InterventionMedecin ne sont pas stockés dans le fichier
        return consultation;
    }

    private String mapToLine(Consultation consultation) {
        return consultation.getIdConsultation() + "-" +
                consultation.getTypeConsultation() + "-" +
                consultation.getDateConsultation();
    }
    private void saveAll(List<Consultation> consultations) throws DaoException {
        fileDatabase.clear();
        for (Consultation consultation : consultations) {
            fileDatabase.save(mapToLine(consultation));
        }
    }
}