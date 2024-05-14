package pl.hop.testData;

import pl.hop.domain.CarHistory;

import java.util.List;

public class CarHistoryTestData {

    public static CarHistory someCarHistory() {
        return CarHistory.builder()
                .carVin("1FT7X2B60FEA74019")
                .carServiceRequests(List.of())
                .build();
    }
}
