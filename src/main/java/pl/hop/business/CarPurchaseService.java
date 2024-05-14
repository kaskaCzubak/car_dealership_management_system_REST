package pl.hop.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hop.domain.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarPurchaseService {

    private final CarService carService;
    private final SalesmanService salesmanService;
    private final CustomerService customerService;

    public List<CarToBuy> availableCars() {
        return carService.findAvailableCars();
    }

    public List<Salesman> availableSalesmen() {
        return salesmanService.findAvailable();
    }

    @Transactional
    public Invoice purchase(final CarPurchaseRequest request) {
        CarToBuy car = carService.findCarToBuy(request.getCarVin());
        Salesman salesman = salesmanService.findSalesman(request.getSalesmanPesel());
        Invoice invoice = buildInvoice(car, salesman);

        Customer customer = findOrCreateCustomer(request);
        customer.addInvoice(invoice);

        customerService.issueInvoice(customer);
        return invoice;
    }

    private Customer findOrCreateCustomer(CarPurchaseRequest request){
        return request.getExistingCustomerEmail().isBlank()
                ? createNewCustomer(request)
                : customerService.findCustomer(request.getExistingCustomerEmail());
    }


    private Customer createNewCustomer(CarPurchaseRequest inputData) {
        return Customer.builder()
            .name(inputData.getCustomerName())
            .surname(inputData.getCustomerSurname())
            .phone(inputData.getCustomerPhone())
            .email(inputData.getCustomerEmail())
            .address(buildAddress(inputData))
            .invoices(new HashSet<>())
            .build();
    }

    private  Address buildAddress(CarPurchaseRequest inputData) {
        return Address.builder()
                .country(inputData.getCustomerAddressCountry())
                .city(inputData.getCustomerAddressCity())
                .postalCode(inputData.getCustomerAddressPostalCode())
                .address(inputData.getCustomerAddressStreet())
                .build();
    }

    private Invoice buildInvoice(CarToBuy car, Salesman salesman) {
        return Invoice.builder()
            .invoiceNumber(UUID.randomUUID().toString())
            .dateTime(OffsetDateTime.of(2025, 10, 1, 12, 0, 0, 0, ZoneOffset.UTC))
            .car(car)
            .salesman(salesman)
            .build();
    }
}
