package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.dao.IDao;
import ma.dentopro.model.Patient;
import ma.dentopro.model.RendezVous;
import ma.dentopro.model.enums.TypeRDV;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDao implements IDao<RendezVous, Long> {

    static File fileBase = new File("myFileBase/RDVaApprouve.txt");

    @Override
    public List<RendezVous> findAll() {
        return readRendezVousFromFile(fileBase);
    }

    @Override
    public RendezVous findById(Long idRDV) {
        List<RendezVous> rendezVousList = readRendezVousFromFile(fileBase);
        for (RendezVous rdv : rendezVousList) {
            if (rdv.getIdRDV().equals(idRDV)) {
                return rdv;
            }
        }
        return null;
    }

    @Override
    public RendezVous save(RendezVous newRendezVous) {
        long newId = generateNewId();
        newRendezVous.setIdRDV(newId);
        writeRendezVousToFile(newRendezVous);
        return newRendezVous;
    }

    @Override
    public List<RendezVous> saveAll(RendezVous... rendezVous) {
        List<RendezVous> savedRendezVous = new ArrayList<>();
        for (RendezVous rdv : rendezVous) {
            RendezVous savedRdv = save(rdv);
            savedRendezVous.add(savedRdv);
        }
        return savedRendezVous;
    }

    @Override
    public boolean update(RendezVous updatedRendezVous) {
        List<RendezVous> rendezVousList = readRendezVousFromFile(fileBase);
        for (int i = 0; i < rendezVousList.size(); i++) {
            if (rendezVousList.get(i).getIdRDV().equals(updatedRendezVous.getIdRDV())) {
                rendezVousList.set(i, updatedRendezVous);
                updateRendezVousFile(rendezVousList);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(Long idRDV) {
        List<RendezVous> rendezVousList = readRendezVousFromFile(fileBase);
        boolean removed = rendezVousList.removeIf(rdv -> rdv.getIdRDV().equals(idRDV));
        if (removed) {
            updateRendezVousFile(rendezVousList);
        }
        return removed;
    }

    @Override
    public boolean delete(RendezVous rendezVous) {
        return deleteById(rendezVous.getIdRDV());
    }

    // Méthodes utilitaires

    private static long generateNewId() {
        long lastId = getLastIdFromFile();
        return lastId + 1;
    }

    private static long getLastIdFromFile() {
        long lastId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileBase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignorer les lignes vides
                }

                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].isEmpty()) {
                    long id = Long.parseLong(parts[0]);
                    if (id > lastId) {
                        lastId = id;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lastId;
    }

    private static List<RendezVous> readRendezVousFromFile(File fileBase) {
        List<RendezVous> rendezVousList = new ArrayList<>();

        if (!fileBase.exists() || fileBase.length() == 0) {
            System.err.println("Le fichier est vide ou n'existe pas.");
            return rendezVousList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileBase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignorer les lignes vides
                }

                RendezVous rdv = convertToRendezVous(line);
                if (rdv != null) {
                    rendezVousList.add(rdv);
                } else {
                    System.err.println("Erreur lors de la conversion de la ligne : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rendezVousList;
    }

    private static RendezVous convertToRendezVous(String fileLine) {
        RendezVous rdv = null;

        // Diviser la ligne en utilisant le séparateur "|"
        String[] parts = fileLine.split("\\|", 6); // Limiter à 6 parties pour éviter de couper la date et l'heure

        // Log pour vérifier les parties de la ligne
        System.out.println("Ligne lue : " + fileLine);
        System.out.println("Nombre de parties : " + parts.length);
        for (int i = 0; i < parts.length; i++) {
            System.out.println("Partie " + i + " : " + parts[i]);
        }

        if (parts.length == 6) { // Vérifier qu'il y a bien 6 parties
            try {
                long idRDV = Long.parseLong(parts[0]); // ID du rendez-vous
                long patientId = Long.parseLong(parts[1]); // ID du patient
                TypeRDV typeRDV = TypeRDV.valueOf(parts[2]); // Type de rendez-vous

                // La date, le motif et l'heure
                String dateRDVStr = parts[3]; // Date du rendez-vous
                String motif = parts[4]; // Motif du rendez-vous
                String tempsStr = parts[5]; // Heure du rendez-vous

                // Convertir la date et l'heure
                LocalDate dateRDV = LocalDate.parse(dateRDVStr); // Conversion de la date
                LocalTime temps = LocalTime.parse(tempsStr); // Conversion de l'heure

                // Créer un objet Patient avec l'ID
                Patient patient = new Patient();
                patient.setId(patientId);

                // Créer l'objet RendezVous
                rdv = new RendezVous(idRDV, typeRDV, dateRDV, motif, temps);
                rdv.setPatient(patient); // Associer le patient au rendez-vous

                System.out.println("Rendez-vous parsé avec succès : " + rdv); // Log pour débogage
            } catch (DateTimeParseException e) {
                System.err.println("Erreur de conversion de date ou d'heure : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur de conversion de TypeRDV ou autre : " + e.getMessage());
            }
        } else {
            System.err.println("Format de ligne invalide : " + fileLine);
        }

        return rdv;
    }

    private static void writeRendezVousToFile(RendezVous rdv) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileBase, true))) {
            String rdvData = convertRendezVousToLine(rdv);
            writer.write(rdvData);
            writer.newLine(); // Assurez-vous que chaque nouvel enregistrement est sur une nouvelle ligne
            System.out.println("Le rendez-vous a été ajouté au fichier avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'écriture du rendez-vous dans le fichier !");
        }
    }

    private static String convertRendezVousToLine(RendezVous rdv) {
        return rdv.getIdRDV() + "|" +
                rdv.getPatient().getId() + "|" + // ID du patient
                rdv.getTypeRDV() + "|" +
                rdv.getDateRDV() + "|" +
                rdv.getMotif() + "|" +
                rdv.getTemps();
    }

    private static void updateRendezVousFile(List<RendezVous> rendezVousList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileBase))) {
            // Écrire les rendez-vous
            for (RendezVous rdv : rendezVousList) {
                writer.write(convertRendezVousToLine(rdv));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du fichier des rendez-vous !");
        }
    }
}