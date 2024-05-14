package pl.hop.business.dao;

import pl.hop.domain.CarHistory;
import pl.hop.domain.CarToService;

import java.util.List;
import java.util.Optional;

public interface CarToServiceDAO {

    Optional<CarToService> findCarToServiceByVin(String vin);

    CarToService saveCarToService(CarToService car);

    CarHistory findCarHistoryByVin(String vin);

    List<CarToService> findAll();
}
