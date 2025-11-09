package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Role;

import java.util.List;

public interface RoleRepo {

    public List<Role> findAll() throws DaoException;
    public Role findById(Long identity) throws DaoException;
    public Role save(Role newElement) throws DaoException;
    public void update(Role newValuesElement) throws DaoException;
    public void delete(Role element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
