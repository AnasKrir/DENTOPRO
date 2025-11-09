package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RevenueRepo;
import ma.dentopro.model.Revenue;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class RevenueRepository implements RevenueRepo {
    private static final String FILE_PATH = "myFileBase/revenues.txt";
    private static final String HEADER = "IDRevenue-Montant"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public RevenueRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<Revenue> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<Revenue> revenues = new ArrayList<>();
        for (String line : lines) {
            revenues.add(mapToRevenue(line));
        }
        return revenues;
    }

    @Override
    public Revenue findById(Long identity) throws DaoException {
        List<Revenue> revenues = findAll();
        for (Revenue revenue : revenues) {
            if (revenue.getIdRevenue().equals(identity)) {
                return revenue;
            }
        }
        return null;
    }

    @Override
    public Revenue save(Revenue newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdRevenue(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(Revenue newValuesElement) throws DaoException {
        List<Revenue> revenues = findAll();
        for (int i = 0; i < revenues.size(); i++) {
            if (revenues.get(i).getIdRevenue().equals(newValuesElement.getIdRevenue())) {
                revenues.set(i, newValuesElement);
                break;
            }
        }
        saveAll(revenues);
    }

    @Override
    public void delete(Revenue element) throws DaoException {
        List<Revenue> revenues = findAll();
        revenues.removeIf(r -> r.getIdRevenue().equals(element.getIdRevenue()));
        saveAll(revenues);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<Revenue> revenues = findAll();
        revenues.removeIf(r -> r.getIdRevenue().equals(identity));
        saveAll(revenues);
    }

    private Revenue mapToRevenue(String line) {
        String[] parts = line.split("-");
        Revenue revenue = new Revenue();
        revenue.setIdRevenue(Long.parseLong(parts[0]));
        revenue.setMontant(Double.parseDouble(parts[1]));
        return revenue;
    }

    private String mapToLine(Revenue revenue) {
        return revenue.getIdRevenue() + "-" +
                revenue.getMontant();
    }

    private void saveAll(List<Revenue> revenues) throws DaoException {
        fileDatabase.clear();
        for (Revenue revenue : revenues) {
            fileDatabase.save(mapToLine(revenue));
        }
    }
}