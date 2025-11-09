package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role) throws DaoException;
    Role updateRole(Role role) throws DaoException;
    void deleteRole(Long id) throws DaoException;
    Role getRoleById(Long id) throws DaoException;
    List<Role> getAllRoles() throws DaoException;
}
