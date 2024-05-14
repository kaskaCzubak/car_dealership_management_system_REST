package pl.hop.business.dao;

import pl.hop.domain.CarToBuy;

import java.util.List;
import java.util.Optional;

public interface CarToBuyDAO {

    Optional<CarToBuy> findCarToBuyByVin(String vin);

    List<CarToBuy> findAvailable();

}
