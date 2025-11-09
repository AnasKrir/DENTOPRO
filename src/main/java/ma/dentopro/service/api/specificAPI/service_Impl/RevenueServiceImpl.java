package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.RevenueRepo;
import ma.dentopro.model.Revenue;
import ma.dentopro.service.api.specificAPI.RevenueService;

import java.util.List;

public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepo revenueRepo;

    public RevenueServiceImpl(RevenueRepo revenueRepo) {
        this.revenueRepo = revenueRepo;
    }

    @Override
    public Revenue createRevenue(Revenue revenue) throws DaoException {
        return revenueRepo.save(revenue);
    }

    @Override
    public Revenue updateRevenue(Revenue revenue) throws DaoException {
        revenueRepo.update(revenue);
        return revenue;
    }

    @Override
    public void deleteRevenue(Long id) throws DaoException {
        revenueRepo.deleteById(id);
    }

    @Override
    public Revenue getRevenueById(Long id) throws DaoException {
        return revenueRepo.findById(id);
    }

    @Override
    public List<Revenue> getAllRevenues() throws DaoException {
        return revenueRepo.findAll();
    }
}
