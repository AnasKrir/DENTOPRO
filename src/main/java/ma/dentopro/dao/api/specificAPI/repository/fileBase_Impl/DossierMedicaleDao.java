package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.dao.IDao;
import ma.dentopro.model.Consultation;
import ma.dentopro.model.Dentiste;
import ma.dentopro.model.DossierMedicale;
import ma.dentopro.model.Patient;
import ma.dentopro.model.enums.StatutPaiement;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DossierMedicaleDao implements IDao<DossierMedicale, Long> {

    static File fileBase = new File("myFileBase/DossiersMedicaux.txt");

    private static long generateNewId() {
        long lastId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileBase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(";"); // Utiliser le bon délimiteur
                    if (parts.length > 0 && !parts[0].isEmpty()) {
                        try {
                            long id = Long.parseLong(parts[0]); // Parser l'ID
                            if (id > lastId) {
                                lastId = id; // Mettre à jour l'ID maximum
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur de format de l'ID dans le fichier : " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lastId + 1; // Retourner l'ID maximum + 1
    }

    public static List<DossierMedicale> readDossiersMedicauxFromFile(File fileBase) {
        List<DossierMedicale> dossiersMedicaux = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileBase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DossierMedicale dossier = convertToDossierMedicale(line);
                if (dossier != null) {
                    dossiersMedicaux.add(dossier);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dossiersMedicaux;
    }

    private static DossierMedicale convertToDossierMedicale(String fileLine) {
        DossierMedicale dossierMedicale = null;
        String[] parts = fileLine.split(";");

        try {
            // Vérifier que la ligne contient suffisamment de parties
            if (parts.length < 6) {
                throw new IllegalArgumentException("Invalid data format: " + fileLine);
            }

            // Parser l'ID du dossier
            long id = parseLongSafe(parts[0]);

            // Parser l'ID du patient
            long patientId = parseLongSafe(parts[1]);

            // Parser l'ID du médecin
            long medecinId = parseLongSafe(parts[2]);

            // Parser les consultations
            List<Consultation> consultations = new ArrayList<>();
            ConsultationDao consultationDao = new ConsultationDao();
            if (!parts[3].isEmpty()) {
                String[] consultationsIds = parts[3].split(",");
                for (String consultationId : consultationsIds) {
                    if (!consultationId.isEmpty()) {
                        Consultation consultation = consultationDao.findById(parseLongSafe(consultationId));
                        if (consultation != null) {
                            consultations.add(consultation);
                        }
                    }
                }
            }

            // Parser la date de création
            LocalDate dateCreation = LocalDate.parse(parts[4]);

            // Parser le statut de paiement
            StatutPaiement statutPaiement = StatutPaiement.valueOf(parts[5]);

            // Récupérer le patient et le médecin
            PatientDao patientDao = new PatientDao();
            DentisteDao dentisteDao = new DentisteDao();
            Patient patient = patientDao.findById(patientId);
            Dentiste medecin = dentisteDao.findById(medecinId);

            // Vérifier que le patient et le médecin existent
            if (patient == null) {
                System.err.println("Patient with ID " + patientId + " not found for line: " + fileLine);
                return null; // Ignorer cette ligne
            }
            if (medecin == null) {
                System.err.println("Dentiste with ID " + medecinId + " not found for line: " + fileLine);
                return null; // Ignorer cette ligne
            }

            // Créer le dossier médical
            dossierMedicale = new DossierMedicale();
            dossierMedicale.setNumeroDossier(id);
            dossierMedicale.setPatient(patient);
            dossierMedicale.setMedecin(medecin);
            dossierMedicale.setConsultations(consultations);
            dossierMedicale.setDateCreation(dateCreation);
            dossierMedicale.setStatutPaiement(statutPaiement);

        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            System.err.println("Conversion impossible: invalid data format! Line: " + fileLine);
        }

        return dossierMedicale;
    }

    public static void writeDossierMedicaleToFile(DossierMedicale dossier) {
        if (dossier == null) {
            throw new IllegalArgumentException("Le dossier médical ne peut pas être null.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileBase, true))) {
            String dossierData = convertDossierMedicaleToLine(dossier);
            writer.write(dossierData);
            writer.newLine();
            System.out.println("Le dossier médical a été ajouté au fichier avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'écriture du dossier médical dans le fichier !");
        }
    }

    private static String convertDossierMedicaleToLine(DossierMedicale dossier) {
        if (dossier == null) {
            throw new IllegalArgumentException("Le dossier médical ne peut pas être null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dossier.getNumeroDossier()).append(";");
        stringBuilder.append(dossier.getPatient().getId()).append(";");
        stringBuilder.append(dossier.getMedecin().getId()).append(";");

        List<Consultation> consultations = dossier.getConsultations();
        if (consultations != null && !consultations.isEmpty()) {
            for (Consultation consultation : consultations) {
                stringBuilder.append(consultation.getIdConsultation()).append(",");
            }
        }
        stringBuilder.append(";");

        stringBuilder.append(dossier.getDateCreation()).append(";");
        stringBuilder.append(dossier.getStatutPaiement().toString());

        return stringBuilder.toString();
    }



    @Override
    public List<DossierMedicale> findAll() {
        return readDossiersMedicauxFromFile(fileBase);
    }

    @Override
    public DossierMedicale findById(Long identifiant) {
        List<DossierMedicale> dossiersMedicaux = readDossiersMedicauxFromFile(fileBase);
        for (DossierMedicale dossier : dossiersMedicaux) {
            if (dossier.getNumeroDossier().equals(identifiant)) {
                return dossier;
            }
        }
        return null;
    }

    @Override
    public DossierMedicale save(DossierMedicale newElement) {
        if (newElement == null) {
            throw new IllegalArgumentException("Le dossier médical ne peut pas être null.");
        }

        // Générer un nouvel ID
        long newId = generateNewId();
        newElement.setNumeroDossier(newId); // Assigner le nouvel ID au dossier médical

        // Écrire le dossier médical dans le fichier
        writeDossierMedicaleToFile(newElement);
        return newElement;
    }

    @Override
    public List<DossierMedicale> saveAll(DossierMedicale... elements) {
        List<DossierMedicale> savedDossiersMedicaux = new ArrayList<>();
        for (DossierMedicale dossier : elements) {
            DossierMedicale savedDossier = save(dossier);
            savedDossiersMedicaux.add(savedDossier);
        }
        return savedDossiersMedicaux;
    }

    @Override
    public boolean update(DossierMedicale updatedElement) {
        List<DossierMedicale> dossiersMedicaux = readDossiersMedicauxFromFile(fileBase);
        for (int i = 0; i < dossiersMedicaux.size(); i++) {
            if (dossiersMedicaux.get(i).getNumeroDossier().equals(updatedElement.getNumeroDossier())) {
                dossiersMedicaux.set(i, updatedElement);
                updateDossiersMedicauxFile(dossiersMedicaux);
                return true;
            }
        }
        return false;
    }

    private void updateDossiersMedicauxFile(List<DossierMedicale> dossiersMedicaux) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileBase))) {
            for (DossierMedicale dossier : dossiersMedicaux) {
                writer.write(convertDossierMedicaleToLine(dossier)); // Convertir le dossier médical en ligne de texte
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du fichier des dossiers médicaux !");
        }
    }

    @Override
    public boolean deleteById(Long identifiant) {
        List<DossierMedicale> dossiersMedicaux = readDossiersMedicauxFromFile(fileBase);
        Iterator<DossierMedicale> iterator = dossiersMedicaux.iterator();
        while (iterator.hasNext()) {
            DossierMedicale dossier = iterator.next();
            if (dossier.getNumeroDossier().equals(identifiant)) {
                iterator.remove();
                updateDossiersMedicauxFile(dossiersMedicaux);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(DossierMedicale element) {
        if (element == null) {
            return false; // Si l'élément est null, rien à supprimer
        }

        List<DossierMedicale> dossiersMedicaux = readDossiersMedicauxFromFile(fileBase);
        boolean removed = dossiersMedicaux.removeIf(dossier -> {
            // Gérer le cas où le numéro de dossier est null
            if (element.getNumeroDossier() == null) {
                return dossier.getNumeroDossier() == null; // Supprimer si le numéro de dossier est null
            } else {
                return element.getNumeroDossier().equals(dossier.getNumeroDossier()); // Supprimer si les numéros correspondent
            }
        });

        if (removed) {
            updateDossiersMedicauxFile(dossiersMedicaux); // Mettre à jour le fichier après suppression
        }

        return removed;
    }

    public List<DossierMedicale> findByIdPatient(long patientId) {
        List<DossierMedicale> dossierMedicaleList = new ArrayList<>();
        List<DossierMedicale> allDossiers = readDossiersMedicauxFromFile(fileBase);

        for (DossierMedicale dossier : allDossiers) {
            if (dossier.getPatient().getId() == patientId) {
                dossierMedicaleList.add(dossier);
            }
        }

        return dossierMedicaleList;
    }
    public boolean deleteDossierMedicale(long patientId) {
        // Lire tous les dossiers médicaux depuis le fichier
        List<DossierMedicale> dossiersMedicaux = readDossiersMedicauxFromFile(fileBase);
        Iterator<DossierMedicale> iterator = dossiersMedicaux.iterator();
        boolean removed = false;

        // Parcourir la liste des dossiers médicaux pour trouver ceux associés au patient
        while (iterator.hasNext()) {
            DossierMedicale dossier = iterator.next();
            if (dossier.getPatient().getId() == patientId) {
                iterator.remove(); // Supprimer le dossier médical de la liste
                removed = true;
            }
        }

        if (removed) {
            // Mettre à jour le fichier des dossiers médicaux après suppression
            updateDossiersMedicauxFile(dossiersMedicaux);
        }

        return removed;
    }
    public DossierMedicale findByPatientId(Long patientId) {
        // Lisez tous les dossiers médicaux depuis votre source de données (par exemple, fichier)
        List<DossierMedicale> allDossiers = findAll();

        // Parcourez la liste des dossiers médicaux et trouvez celui associé au patientId
        for (DossierMedicale dossier : allDossiers) {
            if (dossier.getPatient().getId().equals(patientId)) {
                return dossier;
            }
        }

        // Si aucun dossier médical correspondant n'est trouvé, retournez null
        return null;
    }

    private static long parseLongSafe(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            return 0L; // ou une autre valeur par défaut selon votre besoin
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0L; // ou une autre valeur par défaut selon votre besoin
        }
    }
}



//    public static void main(String[] args) {
//        DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();
//
//        System.out.println("All Dossiers Medicaux:");
//     dossierMedicaleDao.findAll().forEach(System.out::println);

//


//        long dossierId = 1; // Replace this with an existing dossier ID
//        DossierMedicale retrievedDossier = dossierMedicaleDao.findById(dossierId);
//        if (retrievedDossier != null) {
//            System.out.println("Retrieved Dossier Medical:");
//            System.out.println(retrievedDossier);
//        } else {
//            System.out.println("Dossier Medical with ID " + dossierId + " not found!");
//        }

        //dossierMedicaleDao.delete(dossierMedicaleDao.findById(4L));



