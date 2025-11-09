package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ConsultationRepo;
import ma.dentopro.model.Consultation;
import ma.dentopro.service.api.specificAPI.ConsultationService;

import java.util.List;

public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepo consultationRepo;

    public ConsultationServiceImpl(ConsultationRepo consultationRepo) {
        this.consultationRepo = consultationRepo;
    }

    @Override
    public Consultation createConsultation(Consultation consultation) throws DaoException {
        return consultationRepo.save(consultation);
    }

    @Override
    public Consultation updateConsultation(Consultation consultation) throws DaoException {
        consultationRepo.update(consultation);
        return consultation;
    }

    @Override
    public void deleteConsultation(Long id) throws DaoException {
        consultationRepo.deleteById(id);
    }

    @Override
    public Consultation getConsultationById(Long id) throws DaoException {
        return consultationRepo.findById(id);
    }

    @Override
    public List<Consultation> getAllConsultations() throws DaoException {
        return consultationRepo.findAll();
    }
}
