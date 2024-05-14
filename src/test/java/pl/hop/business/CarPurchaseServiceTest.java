package pl.hop.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.domain.*;
import pl.hop.testData.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPurchaseServiceTest {

    @InjectMocks
    private CarPurchaseService carPurchaseService;

    @Mock
    private CarService carService;
    @Mock
    private SalesmanService salesmanService;
    @Mock
    private CustomerService customerService;

    @Test
    void shouldFindAvailableCars() {
        //give
        CarToBuy carToFind1 = CarToBuyTestData.someCarToBuy();
        CarToBuy carToFind2 = CarToBuyTestData.someCarToBuy().withVin("1");
        CarToBuy carToFind3 = CarToBuyTestData.someCarToBuy().withVin("2");
        List<CarToBuy> cars = List.of(carToFind1, carToFind2, carToFind3);
        when(carService.findAvailableCars()).thenReturn(cars);
        // when
        List<CarToBuy> result = carPurchaseService.availableCars();
        // then
        verify(carService).findAvailableCars();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void shouldFindAvailableSalesmen() {
        //give
        Salesman salesmanToFind1 = SalesmanTestData.someSalesman();
        Salesman salesmanToFind2 = SalesmanTestData.someSalesman().withPesel("1");
        Salesman salesmanToFind3 = SalesmanTestData.someSalesman().withPesel("2");
        List<Salesman> availableSalesman = List.of(salesmanToFind1, salesmanToFind2, salesmanToFind3);
        when(salesmanService.findAvailable()).thenReturn(availableSalesman);
        // when
        List<Salesman> result = carPurchaseService.availableSalesmen();
        // then
        verify(salesmanService).findAvailable();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(availableSalesman);
    }

    @Test
    void shouldPurchaseCarWithNewCustomerCorrectly() {
        //given
        CarPurchaseRequest purchaseRequest = CarPurchaseRequestTestData.purchaseRequestWithNewCustomer();
        Invoice invoiceToCreate = InvoiceTestData.invoiceWithoutCustomer();
        when(carService.findCarToBuy(purchaseRequest.getCarVin())).thenReturn(CarToBuyTestData.someCarToBuy());
        when(salesmanService.findSalesman(purchaseRequest.getSalesmanPesel())).thenReturn(SalesmanTestData.someSalesman());
        //when
        Invoice result = carPurchaseService.purchase(purchaseRequest);
        //then
        verify(carService).findCarToBuy(purchaseRequest.getCarVin());
        verify(salesmanService).findSalesman(purchaseRequest.getSalesmanPesel());
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("invoiceNumber")
                .isEqualTo(invoiceToCreate);
    }

    @Test
    void shouldPurchaseCarWithExistingCustomerCorrectly() {
        //given
        CarPurchaseRequest purchaseRequest = CarPurchaseRequestTestData.purchaseRequestWithExistingCustomer();
        Invoice invoiceToCreate = InvoiceTestData.invoiceWithoutCustomer();
        Customer existingCustomer = CustomerTestData.someCustomer();
        when(carService.findCarToBuy(purchaseRequest.getCarVin())).thenReturn(CarToBuyTestData.someCarToBuy());
        when(salesmanService.findSalesman(purchaseRequest.getSalesmanPesel())).thenReturn(SalesmanTestData.someSalesman());
        when(customerService.findCustomer(purchaseRequest.getExistingCustomerEmail())).thenReturn(existingCustomer);
        //when
        Invoice result = carPurchaseService.purchase(purchaseRequest);
        //then
        verify(carService).findCarToBuy(purchaseRequest.getCarVin());
        verify(salesmanService).findSalesman(purchaseRequest.getSalesmanPesel());
        verify(customerService).issueInvoice(existingCustomer);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("invoiceNumber")
                .isEqualTo(invoiceToCreate);
    }
}