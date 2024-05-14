package pl.hop.testData;

import pl.hop.domain.Address;

public class AddressTestData {
    public static Address someAddress() {
        return Address.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("59-200")
                .address("Sikorskiego 1")
                .build();
    }
}
