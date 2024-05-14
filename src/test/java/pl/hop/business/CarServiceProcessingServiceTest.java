package pl.hop.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.ServiceRequestProcessingDAO;
import pl.hop.domain.CarServiceProcessingRequest;
import pl.hop.domain.CarServiceRequest;
import pl.hop.domain.ServiceMechanic;
import pl.hop.domain.ServicePart;
import pl.hop.testData.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceProcessingServiceTest {

    @InjectMocks
    private CarServiceProcessingService carServiceProcessingService;

    @Mock
    private MechanicService mechanicService;
    @Mock
    private ServiceCatalogService serviceCatalogService;
    @Mock
    private CarServiceRequestService carServiceRequestService;
    @Mock
    private PartCatalogService partCatalogService;
    @Mock
    private ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    @Test
    @DisplayName("Processing car service request with completion and part is included")
    void shouldProcessCarServiceRequestCorrectly() {
        // given
        CarServiceProcessingRequest processingRequest = CarServiceProcessingRequestTestData.carServiceProcessingRequestWithCompletion();
        when(mechanicService.findMechanic(processingRequest.getMechanicPesel())).thenReturn(MechanicTestData.someMechanic());
        when(carServiceRequestService.findAnyActiveServiceRequest(processingRequest.getCarVin())).thenReturn(CarServiceRequestTestData.someCarServiceRequest());
        when(serviceCatalogService.findService(processingRequest.getServiceCode())).thenReturn(ServiceTestData.someService());
        when(partCatalogService.findPart(processingRequest.getPartSerialNumber())).thenReturn(PartTestData.somePart());
        doNothing().when(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class), any(ServicePart.class));
        //when, then
        carServiceProcessingService.process(processingRequest);
        verify(mechanicService).findMechanic(processingRequest.getMechanicPesel());
        verify(carServiceRequestService).findAnyActiveServiceRequest(processingRequest.getCarVin());
        verify(serviceCatalogService).findService(processingRequest.getServiceCode());
        verify(partCatalogService).findPart(processingRequest.getPartSerialNumber());
        verify(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class), any(ServicePart.class));
    }

    @Test
    @DisplayName("Processing car service request with completion but part is not included")
    void shouldProcessCarServiceRequestCorrectly2() {
        // given
        CarServiceProcessingRequest processingRequestNoParts = CarServiceProcessingRequestTestData.carServiceProcessingRequestWithCompletion().withPartQuantity(0).withPartSerialNumber(null);
        when(mechanicService.findMechanic(processingRequestNoParts.getMechanicPesel())).thenReturn(MechanicTestData.someMechanic());
        when(carServiceRequestService.findAnyActiveServiceRequest(processingRequestNoParts.getCarVin())).thenReturn(CarServiceRequestTestData.someCarServiceRequest());
        when(serviceCatalogService.findService(processingRequestNoParts.getServiceCode())).thenReturn(ServiceTestData.someService());
        doNothing().when(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class));

        //when, then
        carServiceProcessingService.process(processingRequestNoParts);
        verify(mechanicService).findMechanic(processingRequestNoParts.getMechanicPesel());
        verify(carServiceRequestService).findAnyActiveServiceRequest(processingRequestNoParts.getCarVin());
        verify(serviceCatalogService).findService(processingRequestNoParts.getServiceCode());
        verify(serviceRequestProcessingDAO).process(any(CarServiceRequest.class), any(ServiceMechanic.class));
    }


}