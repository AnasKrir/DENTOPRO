package ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RoleRepo;
import ma.dentopro.model.enums.Role;
import ma.dentopro.utils.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements RoleRepo {
    private static final String FILE_PATH = "myFileBase/roles.txt";
    private static final String HEADER = "IDRole-NomRole"; // En-tête pour actes.txt
    private final FileDatabase fileDatabase;

    public RoleRepository() {
        this.fileDatabase = new FileDatabase(FILE_PATH, HEADER);
    }

    @Override
    public List<ma.dentopro.model.Role> findAll() throws DaoException {
        List<String> lines = fileDatabase.load();
        List<ma.dentopro.model.Role> roles = new ArrayList<>();
        for (String line : lines) {
            roles.add(mapToRole(line));
        }
        return roles;
    }

    @Override
    public ma.dentopro.model.Role findById(Long identity) throws DaoException {
        List<ma.dentopro.model.Role> roles = findAll();
        for (ma.dentopro.model.Role role : roles) {
            if (role.getIdRole().equals(identity)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public ma.dentopro.model.Role save(ma.dentopro.model.Role newElement) throws DaoException {
        long newId = fileDatabase.generateNewId(); // Générer un nouvel ID
        newElement.setIdRole(Long.valueOf(newId)); // Définir l'ID
        String line = mapToLine(newElement);
        fileDatabase.save(line);
        return newElement;
    }

    @Override
    public void update(ma.dentopro.model.Role newValuesElement) throws DaoException {
        List<ma.dentopro.model.Role> roles = findAll();
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRole().equals(newValuesElement.getIdRole())) {
                roles.set(i, newValuesElement);
                break;
            }
        }
        saveAll(roles);
    }

    @Override
    public void delete(ma.dentopro.model.Role element) throws DaoException {
        List<ma.dentopro.model.Role> roles = findAll();
        roles.removeIf(r -> r.getIdRole().equals(element.getIdRole()));
        saveAll(roles);
    }

    @Override
    public void deleteById(Long identity) throws DaoException {
        List<ma.dentopro.model.Role> roles = findAll();
        roles.removeIf(r -> r.getIdRole().equals(identity));
        saveAll(roles);
    }

    private ma.dentopro.model.Role mapToRole(String line) {
        String[] parts = line.split("-");
        ma.dentopro.model.Role role = new ma.dentopro.model.Role();
        role.setIdRole(Long.parseLong(parts[0]));
        role.setNomRole(Role.valueOf(parts[1]));
        // Les privilèges ne sont pas stockés dans le fichier
        return role;
    }

    private String mapToLine(ma.dentopro.model.Role role) {
        return role.getIdRole() + "-" +
                role.getNomRole();
    }
    private void saveAll(List<ma.dentopro.model.Role> roles) throws DaoException {
        fileDatabase.clear();
        for (ma.dentopro.model.Role role : roles) {
            fileDatabase.save(mapToLine(role));
        }
    }
}