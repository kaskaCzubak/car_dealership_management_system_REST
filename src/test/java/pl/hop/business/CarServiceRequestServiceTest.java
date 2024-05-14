package pl.hop.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.CarServiceRequestDAO;
import pl.hop.domain.CarServiceRequest;
import pl.hop.domain.CarToService;
import pl.hop.domain.Mechanic;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.domain.exception.ProcessingException;
import pl.hop.testData.CarServiceRequestTestData;
import pl.hop.testData.CarToServiceTestData;
import pl.hop.testData.MechanicTestData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceRequestServiceTest {

    @InjectMocks
    private CarServiceRequestService carServiceRequestService;

    @Mock
    private MechanicService mechanicService;
    @Mock
    private CarService carService;
    @Mock
    private CustomerService customerService;
    @Mock
    private CarServiceRequestDAO carServiceRequestDAO;

    @Test
    void shouldFindAvailableMechanics() {
        //give
        Mechanic mechanicToFind1 = MechanicTestData.someMechanic();
        Mechanic mechanicToFind2 = MechanicTestData.someMechanic().withPesel("67111001111");
        Mechanic mechanicToFind3 = MechanicTestData.someMechanic().withPesel("82111001111");
        List<Mechanic> mechanics = List.of(mechanicToFind1, mechanicToFind2, mechanicToFind3);
        when(mechanicService.findAvailable()).thenReturn(mechanics);
        // when
        List<Mechanic> result = carServiceRequestService.availableMechanics();
        // then
        verify(mechanicService).findAvailable();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(mechanics);
    }

    @Test
    void shouldFindAvailableServiceRequests() {
            //give
            CarServiceRequest carServiceRequestToFind1 = CarServiceRequestTestData.someCarServiceRequest();
            CarServiceRequest carServiceRequestToFind2 = CarServiceRequestTestData.someCarServiceRequest().withCarServiceRequestNumber("2027.0.11-10.2.10.33");
            CarServiceRequest carServiceRequestToFind3 = CarServiceRequestTestData.someCarServiceRequest().withCarServiceRequestNumber("2027.0.12-10.2.10.33");
            List<CarServiceRequest> carRequests = List.of(carServiceRequestToFind1, carServiceRequestToFind2, carServiceRequestToFind3);
            when(carServiceRequestDAO.findAvailable()).thenReturn(carRequests);
            // when
            List<CarServiceRequest> result = carServiceRequestService.availableServiceRequests();
            // then
            verify(carServiceRequestDAO).findAvailable();
            assertThat(result).hasSize(3);
            assertThat(result).isEqualTo(carRequests);
    }

    @Test
    void shouldMakeServiceRequestWithNewCar() {
        //given
        CarServiceRequest serviceRequestCarExists = CarServiceRequestTestData.someCarServiceRequest();
        when(carService.saveCarToService(serviceRequestCarExists.getCar())).thenReturn(serviceRequestCarExists.getCar());
        when(customerService.saveCustomer(serviceRequestCarExists.getCustomer())).thenReturn(serviceRequestCarExists.getCustomer());
        //when, then
        carServiceRequestService.makeServiceRequest(serviceRequestCarExists);
        verify(carService).saveCarToService(serviceRequestCarExists.getCar());
        verify(customerService).saveCustomer(serviceRequestCarExists.getCustomer());
        verify(customerService).saveServiceRequest(serviceRequestCarExists.getCustomer());
    }
    @Test
    void shouldMakeServiceRequestWithExistingCarInCarToBuy() {
        //given
        CarToService carExistsInCarToBuy = CarToServiceTestData.someCarToService().withBrand(null).withModel(null).withYear(null);
        CarServiceRequest serviceRequestCarExists = CarServiceRequestTestData.someCarServiceRequest().withCar(carExistsInCarToBuy);
        when(carService.findCarToService(serviceRequestCarExists.getCar().getVin())).thenReturn(Optional.of(serviceRequestCarExists.getCar()));
        when(customerService.findCustomer(serviceRequestCarExists.getCustomer().getEmail())).thenReturn(serviceRequestCarExists.getCustomer());
        //when, then
        carServiceRequestService.makeServiceRequest(serviceRequestCarExists);
        verify(carService).findCarToService(serviceRequestCarExists.getCar().getVin());
        verify(customerService).findCustomer(serviceRequestCarExists.getCustomer().getEmail());
        verify(customerService).saveServiceRequest(serviceRequestCarExists.getCustomer());
    }

    @Test
    void shouldFindAnyActiveServiceRequest() {
        //given
        CarServiceRequest activeCarServiceRequest = CarServiceRequestTestData.someCarServiceRequest();
        when(carServiceRequestDAO.findActiveServiceRequestsByCarVin(activeCarServiceRequest.getCar().getVin()))
                .thenReturn(Set.of(activeCarServiceRequest));
        //when
        CarServiceRequest result = carServiceRequestService.findAnyActiveServiceRequest(activeCarServiceRequest.getCar().getVin());
        //then
        verify(carServiceRequestDAO).findActiveServiceRequestsByCarVin(activeCarServiceRequest.getCar().getVin());
        assertThat(result).isEqualTo(activeCarServiceRequest);
    }

    @Test
    void shouldThrowWhenNoFindingAnyServiceRequests() {
        // given
        String carVin = "1FT7X2B60FEA74019";
        // when, then
        assertThatThrownBy(() -> { throw new NotFoundException(
                String.format("Could not find any service requests, car vin: [%s]", carVin)); })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Could not find any service requests, car vin: [%s]", carVin));
    }

    @Test
    void shouldFailWhenMoreThanOneActiveServiceRequest() {
        // given
        CarServiceRequest activeCarServiceRequest1 = CarServiceRequestTestData.someCarServiceRequest();
        CarServiceRequest activeCarServiceRequest2 = CarServiceRequestTestData.someCarServiceRequest().withCarServiceRequestNumber("1");
        Set<CarServiceRequest> serviceRequestsMoreThanOneActive = Set.of(activeCarServiceRequest1, activeCarServiceRequest2);
        when(carServiceRequestDAO.findActiveServiceRequestsByCarVin(activeCarServiceRequest1.getCar().getVin())).thenReturn(serviceRequestsMoreThanOneActive);
//        // when, then
        try {
            CarServiceRequest result = carServiceRequestService.findAnyActiveServiceRequest(activeCarServiceRequest1.getCar().getVin());
        }catch (ProcessingException e){
            verify(carServiceRequestDAO).findActiveServiceRequestsByCarVin(activeCarServiceRequest1.getCar().getVin());
            assertThat(e.getMessage()).isEqualTo("There should be only one active service request at a time, car vin: [%s]".formatted(activeCarServiceRequest1.getCar().getVin()));
        }
    }
}