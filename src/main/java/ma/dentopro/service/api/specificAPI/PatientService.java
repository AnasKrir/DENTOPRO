package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Patient;

import java.util.List;

public interface PatientService {

    Patient createPatient(Patient patient) throws DaoException;
    Patient updatePatient(Patient patient) throws DaoException;
    void deletePatient(Long id) throws DaoException;
    Patient getPatientById(Long id) throws DaoException;
    List<Patient> getAllPatients() throws DaoException;
}
