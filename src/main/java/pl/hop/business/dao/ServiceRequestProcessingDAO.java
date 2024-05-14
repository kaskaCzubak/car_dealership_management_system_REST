package pl.hop.business.dao;

import pl.hop.domain.CarServiceRequest;
import pl.hop.domain.ServiceMechanic;
import pl.hop.domain.ServicePart;

public interface ServiceRequestProcessingDAO {
    void process(CarServiceRequest serviceRequest, ServiceMechanic serviceMechanic);

    void process(CarServiceRequest serviceRequest, ServiceMechanic serviceMechanic, ServicePart servicePart);
}
