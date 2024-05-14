package pl.hop.testData;

import pl.hop.domain.Salesman;

import java.util.Set;

public class SalesmanTestData {
    public static Salesman someSalesman(){
        return Salesman.builder()
                .name("Jack")
                .surname("McFlurry")
                .pesel("67111009269")
                .invoices(Set.of())
                .build();
    }
}
