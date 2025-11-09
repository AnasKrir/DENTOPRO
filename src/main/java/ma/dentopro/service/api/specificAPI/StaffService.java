package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Staff;

import java.util.List;

public interface StaffService {

    Staff createStaff(Staff staff) throws DaoException;
    Staff updateStaff(Staff staff) throws DaoException;
    void deleteStaff(Long id) throws DaoException;
    Staff getStaffById(Long id) throws DaoException;
    List<Staff> getAllStaff() throws DaoException;
}
