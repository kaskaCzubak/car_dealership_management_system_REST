package pl.hop.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.CarToBuyDAO;
import pl.hop.business.dao.CarToServiceDAO;
import pl.hop.business.mapper.CarToServiceMapper;
import pl.hop.domain.CarHistory;
import pl.hop.domain.CarToBuy;
import pl.hop.domain.CarToService;
import pl.hop.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CarService {

    private final CarToBuyDAO carToBuyDAO;
    private final CarToServiceDAO carToServiceDAO;


    public List<CarToBuy> findAvailableCars() {
        List<CarToBuy> availableCars = carToBuyDAO.findAvailable();
        log.info("Available cars: [{}]", availableCars.size());
        return availableCars;
    }


    public CarToBuy findCarToBuy(String vin) {

        return carToBuyDAO.findCarToBuyByVin(vin)
                .orElseThrow(() -> new NotFoundException("Could not find car by vin: [%s]".formatted(vin)));
    }


    public Optional<CarToService> findCarToService(String vin) {
        return carToServiceDAO.findCarToServiceByVin(vin);
    }


    public CarToService saveCarToService(CarToBuy carToBuy) {
        CarToService carToService = CarToServiceMapper.map(carToBuy);
        return carToServiceDAO.saveCarToService(carToService);
    }

    public CarToService saveCarToService(CarToService car) {
        return carToServiceDAO.saveCarToService(car);
    }

    public List<CarToService> findAllCarsWithHistory() {
        List<CarToService> allCars = carToServiceDAO.findAll();
        log.info("Cars to show history: [{}]", allCars.size());
        return allCars;
    }

    public CarHistory findCarHistoryByVin(String carVin) {
        return carToServiceDAO.findCarHistoryByVin(carVin);
    }
}
