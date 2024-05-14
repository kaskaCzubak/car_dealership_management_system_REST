package pl.hop.testData;

import pl.hop.domain.Mechanic;

import java.util.Set;

public class MechanicTestData {

    public static Mechanic someMechanic(){
        return Mechanic.builder()
                .name("Tom")
                .surname("Jefferson")
                .pesel("67111009269")
                .serviceMechanics(Set.of())
                .build();
    }
}
