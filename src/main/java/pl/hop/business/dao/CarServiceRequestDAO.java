package pl.hop.business.dao;

import pl.hop.domain.CarServiceRequest;

import java.util.List;
import java.util.Set;

public interface CarServiceRequestDAO {
    List<CarServiceRequest> findAvailable();

    Set<CarServiceRequest> findActiveServiceRequestsByCarVin(String carVin);
}
