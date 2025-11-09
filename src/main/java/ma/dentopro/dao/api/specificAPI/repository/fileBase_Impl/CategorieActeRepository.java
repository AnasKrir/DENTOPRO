package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.CategorieActeRepo;
import ma.dentopro.model.CategorieActe;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategorieActeRepository implements CategorieActeRepo {

    private static final String FILE_PATH = "myFileBase/categoriesActes.txt";
    private static final String HEADER = "IDCatégorieActe-Libelle"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public CategorieActeRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<CategorieActe> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<CategorieActe> categories = new ArrayList<>();
        for (String line : lines) {
            categories.add(mapToCategorieActe(line));
        }
        return categories;
    }

    @Override
    public CategorieActe findById(Long id) throws DaoException {
        List<CategorieActe> categories = findAll();
        for (CategorieActe categorie : categories) {
            if (categorie.getIdCategorieActe().equals(id)) {
                return categorie;
            }
        }
        return null;
    }

    @Override
    public CategorieActe save(CategorieActe newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdCategorieActe(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(CategorieActe categorieActe) throws DaoException {
        List<CategorieActe> categories = findAll();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getIdCategorieActe().equals(categorieActe.getIdCategorieActe())) {
                categories.set(i, categorieActe);
                break;
            }
        }
        saveAll(categories);
    }

    @Override
    public void delete(CategorieActe categorieActe) throws DaoException {
        List<CategorieActe> categories = findAll();
        categories.removeIf(c -> c.getIdCategorieActe().equals(categorieActe.getIdCategorieActe()));
        saveAll(categories);
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        List<CategorieActe> categories = findAll();
        categories.removeIf(c -> c.getIdCategorieActe().equals(id));
        saveAll(categories);
    }


    private CategorieActe mapToCategorieActe(String line) {
        String[] parts = line.split("-");
        CategorieActe categorieActe = new CategorieActe();
        categorieActe.setIdCategorieActe(Long.parseLong(parts[0]));
        categorieActe.setLibelle(parts[1]);
        return categorieActe;
    }


    private String mapToLine(CategorieActe categorieActe) {
        return categorieActe.getIdCategorieActe() + "-" +
                categorieActe.getLibelle();
    }


    private void saveAll(List<CategorieActe> categories) throws DaoException {
        fileDatabase.clear();
        for (CategorieActe categorie : categories) {
            fileDatabase.save(mapToLine(categorie));
        }
    }
}