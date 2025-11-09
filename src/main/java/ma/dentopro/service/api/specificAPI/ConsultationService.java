package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Consultation;

import java.util.List;

public interface ConsultationService {

    Consultation createConsultation(Consultation consultation) throws DaoException;
    Consultation updateConsultation(Consultation consultation) throws DaoException;
    void deleteConsultation(Long id) throws DaoException;
    Consultation getConsultationById(Long id) throws DaoException;
    List<Consultation> getAllConsultations() throws DaoException;
}
