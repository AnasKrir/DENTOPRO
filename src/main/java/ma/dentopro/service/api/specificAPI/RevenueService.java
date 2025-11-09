package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Revenue;

import java.util.List;

public interface RevenueService {

    Revenue createRevenue(Revenue revenue) throws DaoException;
    Revenue updateRevenue(Revenue revenue) throws DaoException;
    void deleteRevenue(Long id) throws DaoException;
    Revenue getRevenueById(Long id) throws DaoException;
    List<Revenue> getAllRevenues() throws DaoException;
}
