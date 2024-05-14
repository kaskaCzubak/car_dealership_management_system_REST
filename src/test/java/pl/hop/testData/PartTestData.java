package pl.hop.testData;

import pl.hop.domain.Part;

import java.math.BigDecimal;

public class PartTestData {

    public static Part somePart(){
        return Part.builder()
                .serialNumber("11523-7310")
                .description("Tire")
                .price(new BigDecimal("100.59"))
                .build();
    }
}
