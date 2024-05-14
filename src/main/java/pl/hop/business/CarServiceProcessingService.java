package pl.hop.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.hop.business.dao.ServiceRequestProcessingDAO;
import pl.hop.domain.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CarServiceProcessingService {

    private final MechanicService mechanicService;
    private final ServiceCatalogService serviceCatalogService;
    private final PartCatalogService partCatalogService;
    private final CarServiceRequestService carServiceRequestService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    @Transactional
    public void process(CarServiceProcessingRequest request) {
        Mechanic mechanic = mechanicService.findMechanic(request.getMechanicPesel());
        CarServiceRequest serviceRequest = carServiceRequestService.findAnyActiveServiceRequest(request.getCarVin());
        Service service = serviceCatalogService.findService(request.getServiceCode());
        ServiceMechanic serviceMechanic = buildServiceMechanic(request, mechanic, serviceRequest, service);

        handleServiceRequestCompletion(request, serviceRequest);

        processRequest(request, serviceRequest, serviceMechanic);
    }

    private void processRequest(CarServiceProcessingRequest request,
                                CarServiceRequest serviceRequest,
                                ServiceMechanic serviceMechanic) {

        if (request.partNotIncluded()) {
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic);
        } else {
            Part part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePart servicePart = buildServicePart(request, serviceRequest, part);
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic, servicePart);
        }
    }

    private void handleServiceRequestCompletion(CarServiceProcessingRequest request, CarServiceRequest serviceRequest) {
        if (request.isDone()) {
            serviceRequest.setCompletedDateTime(OffsetDateTime.of(2029, 3, 2, 10, 9, 12, 0, ZoneOffset.UTC));
        }
    }
    private ServiceMechanic buildServiceMechanic(
        CarServiceProcessingRequest request,
        Mechanic mechanic,
        CarServiceRequest serviceRequest,
        Service service
    ) {
        return ServiceMechanic.builder()
            .hours(request.getHours())
            .comment(request.getComment())
            .carServiceRequest(serviceRequest)
            .mechanic(mechanic)
            .service(service)
            .build();
    }

    private ServicePart buildServicePart(
        CarServiceProcessingRequest request,
        CarServiceRequest serviceRequest,
        Part part
    ) {
        return ServicePart.builder()
            .quantity(request.getPartQuantity())
            .carServiceRequest(serviceRequest)
            .part(part)
            .build();
    }
}
