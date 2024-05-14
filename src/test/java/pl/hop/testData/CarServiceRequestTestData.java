package pl.hop.testData;

import pl.hop.domain.CarServiceRequest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class CarServiceRequestTestData {

    public static CarServiceRequest someCarServiceRequest() {
        return CarServiceRequest.builder()
                .carServiceRequestNumber("2027.0.10-10.2.10.33")
                .customer(CustomerTestData.someCustomer())
                .car(CarToServiceTestData.someCarToService())
                .receivedDateTime(OffsetDateTime.of(2024, 5, 12, 8, 20, 0, 0, ZoneOffset.UTC))
                .customerComment("New Comment")
                .build();
    }


}
