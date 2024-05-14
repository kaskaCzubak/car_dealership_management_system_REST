package pl.hop.testData;

import pl.hop.domain.CarServiceProcessingRequest;

public class CarServiceProcessingRequestTestData {


    public static CarServiceProcessingRequest carServiceProcessingRequestWithCompletion() {
        return CarServiceProcessingRequest.builder()
                .mechanicPesel("67111009269")
                .carVin("1FT7X2B60FEA74019")
                .partSerialNumber("11523-7310")
                .partQuantity(2)
                .serviceCode("58394-014")
                .hours(1)
                .done(true)
                .build();
    }
}
