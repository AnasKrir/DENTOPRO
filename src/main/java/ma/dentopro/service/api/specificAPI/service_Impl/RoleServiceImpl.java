package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RoleRepo;
import ma.dentopro.model.Role;
import ma.dentopro.service.api.specificAPI.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role createRole(Role role) throws DaoException {
        return roleRepo.save(role);
    }

    @Override
    public Role updateRole(Role role) throws DaoException {
        roleRepo.update(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) throws DaoException {
        roleRepo.deleteById(id);
    }

    @Override
    public Role getRoleById(Long id) throws DaoException {
        return roleRepo.findById(id);
    }

    @Override
    public List<Role> getAllRoles() throws DaoException {
        return roleRepo.findAll();
    }
}
