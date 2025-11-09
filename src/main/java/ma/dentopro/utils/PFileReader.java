package ma.dentopro.utils;
import ma.dentopro.model.Patient;
import ma.dentopro.model.enums.CategorieAntecedentsMedicaux;
import ma.dentopro.model.enums.GroupeSanguin;
import ma.dentopro.model.enums.Mutuelle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PFileReader {


    public static List<Patient> readPatientsFromFile(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 11) {
                    String categoryString = parts[10].trim();
                    CategorieAntecedentsMedicaux category;
                    try {
                        category = CategorieAntecedentsMedicaux.valueOf(categoryString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid category: " + categoryString + " for line: " + line);
                        continue;
                    }

                    Patient patient = new Patient(
                            parts[1],
                            parts[2],
                            parts[3],
                            Long.parseLong(parts[0]),
                            parts[4],
                            parts[5],
                            parts[6],
                            LocalDate.parse(parts[7]),
                            Mutuelle.valueOf(parts[8]),
                            GroupeSanguin.valueOf(parts[9]),
                            Collections.singletonList(category),
                            parts[11]
                    );

                    patients.add(patient);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }
        return patients;
    }



}
