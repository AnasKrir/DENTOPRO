package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.StaffRepo;
import ma.dentopro.model.Staff;
import ma.dentopro.service.api.specificAPI.StaffService;

import java.util.List;

public class StaffServiceImpl implements StaffService {

    private final StaffRepo staffRepo;

    public StaffServiceImpl(StaffRepo staffRepo) {
        this.staffRepo = staffRepo;
    }

    @Override
    public Staff createStaff(Staff staff) throws DaoException {
        return staffRepo.save(staff);
    }

    @Override
    public Staff updateStaff(Staff staff) throws DaoException {
        staffRepo.update(staff);
        return staff;
    }

    @Override
    public void deleteStaff(Long id) throws DaoException {
        staffRepo.deleteById(id);
    }

    @Override
    public Staff getStaffById(Long id) throws DaoException {
        return staffRepo.findById(id);
    }

    @Override
    public List<Staff> getAllStaff() throws DaoException {
        return staffRepo.findAll();
    }
}
