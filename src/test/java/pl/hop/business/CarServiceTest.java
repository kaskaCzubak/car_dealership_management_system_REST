package pl.hop.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.CarToBuyDAO;
import pl.hop.business.dao.CarToServiceDAO;
import pl.hop.business.mapper.CarToServiceMapper;
import pl.hop.domain.CarHistory;
import pl.hop.domain.CarToBuy;
import pl.hop.domain.CarToService;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.CarHistoryTestData;
import pl.hop.testData.CarToBuyTestData;
import pl.hop.testData.CarToServiceTestData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarToBuyDAO carToBuyDAO;
    @Mock
    private CarToServiceDAO carToServiceDAO;

    @Test
    void shouldFindAvailableCarsToBuy() {
        //give
        CarToBuy carToFind1 = CarToBuyTestData.someCarToBuy();
        CarToBuy carToFind2 = CarToBuyTestData.someCarToBuy().withVin("1");
        CarToBuy carToFind3 = CarToBuyTestData.someCarToBuy().withVin("2");
        List<CarToBuy> cars = List.of(carToFind1, carToFind2, carToFind3);

        when(carToBuyDAO.findAvailable()).thenReturn(cars);
        // when
        List<CarToBuy> result = carService.findAvailableCars();
        // then
        verify(carToBuyDAO).findAvailable();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void shouldFindCarToBuyWithVin() {
        // given
        CarToBuy carWithVin = CarToBuyTestData.someCarToBuy();
        when(carToBuyDAO.findCarToBuyByVin(carWithVin.getVin())).thenReturn(Optional.of(carWithVin));
        // when
        CarToBuy result = carService.findCarToBuy(carWithVin.getVin());
        // then
        verify(carToBuyDAO).findCarToBuyByVin(carWithVin.getVin());
        assertThat(result).isEqualTo(carWithVin);
    }

    @Test
    void shouldThrowWhenFindingNoExistingVin() {
        // given
        CarToBuy carWithNoExistingVin = CarToBuyTestData.someCarToBuy();
        // when, then
        assertThatThrownBy(() -> { throw new NotFoundException(
                    String.format("Could not find car by vin: [%s]", carWithNoExistingVin.getVin())); })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Could not find car by vin: [%s]", carWithNoExistingVin.getVin()));
    }

    @Test
    void shouldFindCarToServiceWithVin() {
        // given
        CarToService carToServiceWithVin = CarToServiceTestData.someCarToService();
        when(carToServiceDAO.findCarToServiceByVin(carToServiceWithVin.getVin())).thenReturn(Optional.of(carToServiceWithVin));
        // when
        Optional<CarToService> result = carService.findCarToService(carToServiceWithVin.getVin());
        // then
        verify(carToServiceDAO).findCarToServiceByVin(carToServiceWithVin.getVin());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(carToServiceWithVin);
    }

    @Test
    void shouldSaveCarToBuyAsCarToServiceCorrectly() {
        //give
        CarToBuy carToBuyToSave = CarToBuyTestData.someCarToBuy();
        CarToService expectedCarToService = CarToServiceTestData.someCarToService();

        try(var mockedStatic = Mockito.mockStatic(CarToServiceMapper.class)){
            mockedStatic.when(()->CarToServiceMapper.map(carToBuyToSave)).thenReturn(expectedCarToService);
            when(carToServiceDAO.saveCarToService(expectedCarToService)).thenReturn(expectedCarToService);
        }
        //when
        CarToService result = carService.saveCarToService(carToBuyToSave);
        //then
        verify(carToServiceDAO).saveCarToService(expectedCarToService);
        assertThat(result).isEqualTo(expectedCarToService);
    }


    @Test
    void shouldSaveCarToServiceCorrectly() {
        // given
        CarToService carToSave = CarToServiceTestData.someCarToService();
        when(carToServiceDAO.saveCarToService(carToSave)).thenReturn(carToSave);        // when
        //when
        CarToService result = carService.saveCarToService(carToSave);
        // then
        verify(carToServiceDAO).saveCarToService(carToSave);
        assertThat(result).isEqualTo(carToSave);
    }

    @Test
    void shouldFindAllCarsWithHistory() {
        //give
        CarToService carWithHistory1 = CarToServiceTestData.someCarToService();
        CarToService carWithHistory2 = CarToServiceTestData.someCarToService().withVin("1");
        CarToService carWithHistory3 = CarToServiceTestData.someCarToService().withVin("2");
        List<CarToService> cars = List.of(carWithHistory1, carWithHistory2, carWithHistory3);
        when(carToServiceDAO.findAll()).thenReturn(cars);
        // when
        List<CarToService> result = carService.findAllCarsWithHistory();
        // then
        verify(carToServiceDAO).findAll();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void shouldFindCarHistoryByVin() {
        // given
        CarHistory carHistory = CarHistoryTestData.someCarHistory();
        when(carToServiceDAO.findCarHistoryByVin(carHistory.getCarVin())).thenReturn(carHistory);
        // when
        CarHistory result = carService.findCarHistoryByVin(carHistory.getCarVin());
        // then
        verify(carToServiceDAO).findCarHistoryByVin(carHistory.getCarVin());
        assertThat(result).isEqualTo(carHistory);
    }
}