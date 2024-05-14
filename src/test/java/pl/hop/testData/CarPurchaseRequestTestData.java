package pl.hop.testData;

import pl.hop.domain.CarPurchaseRequest;

public class CarPurchaseRequestTestData {
    public static CarPurchaseRequest purchaseRequestWithExistingCustomer() {
        return CarPurchaseRequest.builder()
                .existingCustomerEmail("will.huston@email.com")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("67111009269")
                .build();
    }

    public static CarPurchaseRequest purchaseRequestWithNewCustomer() {
        return CarPurchaseRequest.builder()
                .existingCustomerEmail("")
                .customerName("Will")
                .customerSurname("Huston")
                .customerPhone("+48 754 552 234")
                .customerEmail("will.huston@email.com")
                .customerAddressCountry("Poland")
                .customerAddressCity("Warsaw")
                .customerAddressPostalCode("59-200")
                .customerAddressStreet("Sikorskiego 1")
                .carVin("1FT7X2B60FEA74019")
                .salesmanPesel("67111009269")
                .build();
    }
}
