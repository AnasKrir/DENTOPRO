package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Staff;

import java.util.List;

public interface StaffRepo {

    public List<Staff> findAll() throws DaoException;
    public Staff findById(Long identity) throws DaoException;
    public Staff save(Staff newElement) throws DaoException;
    public void update(Staff newValuesElement) throws DaoException;
    public void delete(Staff element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;

}
