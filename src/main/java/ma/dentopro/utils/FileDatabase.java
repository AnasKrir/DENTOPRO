package ma.dentopro.utils;

import ma.dentopro.exceptions.DaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDatabase {
    private String filePath;
    private String header; // Ligne d'en-tête

    public FileDatabase(String filePath, String header) {
        this.filePath = filePath;
        this.header = header;
        initializeFile(); // Initialiser le fichier avec l'en-tête si nécessaire
    }

    private void initializeFile() {
        File file = new File(filePath);
        boolean fileExists = file.exists();
        boolean isEmpty = file.length() == 0;

        if (!fileExists || isEmpty) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(header); // Ajouter l'en-tête
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> load() throws DaoException {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // Sauter la première ligne (en-tête)
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            throw new DaoException("Failed to load data from file", e);
        }
        return data;
    }

    public void save(String data) throws DaoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            throw new DaoException("Failed to save data to file", e);
        }
    }

    public void saveAll(List<String> data) throws DaoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(header); // Écrire l'en-tête
            writer.newLine();
            for (String line : data) {
                writer.write(line); // Écrire chaque ligne de données
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DaoException("Failed to save all data to file", e);
        }
    }

    public void clear() throws DaoException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print(""); // Vider le fichier
        } catch (IOException e) {
            throw new DaoException("Failed to clear file", e);
        }

        // Réinitialiser l'en-tête après avoir vidé le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(header); // Réécrire l'en-tête
            writer.newLine();
        } catch (IOException e) {
            throw new DaoException("Failed to reinitialize file header", e);
        }
    }

    public void update(String oldLine, String newLine) throws DaoException {
        List<String> lines = load(); // Charger toutes les lignes
        int index = lines.indexOf(oldLine); // Trouver l'index de la ligne à mettre à jour
        if (index != -1) {
            lines.set(index, newLine); // Remplacer l'ancienne ligne par la nouvelle
            saveAll(lines); // Réécrire toutes les lignes dans le fichier
        } else {
            throw new DaoException("Line to update not found: " + oldLine);
        }
    }

    public void delete(String lineToDelete) throws DaoException {
        List<String> lines = load(); // Charger toutes les lignes
        if (lines.remove(lineToDelete)) { // Supprimer la ligne si elle existe
            saveAll(lines); // Réécrire toutes les lignes dans le fichier
        } else {
            throw new DaoException("Line to delete not found: " + lineToDelete);
        }
    }

    public long generateNewId() {
        long lastId = getLastIdFromFile();
        return lastId + 1;
    }

    private long getLastIdFromFile() {
        long lastId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split("-"); // Utiliser "-" comme séparateur
                    if (parts.length > 0 && !parts[0].isEmpty()) {
                        long id = Long.parseLong(parts[0]);
                        if (id > lastId) {
                            lastId = id;
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return lastId;
    }
}