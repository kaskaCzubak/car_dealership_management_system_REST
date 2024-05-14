package pl.hop.testData;

import pl.hop.domain.Customer;

import java.util.HashSet;

public class CustomerTestData {

    public static Customer someCustomer() {
        return Customer.builder()
                .name("Will")
                .surname("Huston")
                .phone("+48 754 552 234")
                .email("will.huston@email.com")
                .address(AddressTestData.someAddress())
                .invoices(new HashSet<>())
                .carServiceRequests(new HashSet<>())
                .build();
    }

}
