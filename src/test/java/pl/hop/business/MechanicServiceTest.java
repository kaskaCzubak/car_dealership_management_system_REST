package pl.hop.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.MechanicDAO;
import pl.hop.domain.Mechanic;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.MechanicTestData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MechanicServiceTest {

    @InjectMocks
    private MechanicService mechanicService;

    @Mock
    private MechanicDAO mechanicDAO;

    @Test
    void shouldFindAvailableMechanics() {
        //give
        Mechanic mechanicToFind1 = MechanicTestData.someMechanic();
        Mechanic mechanicToFind2 = MechanicTestData.someMechanic().withPesel("67111001111");
        Mechanic mechanicToFind3 = MechanicTestData.someMechanic().withPesel("82111001111");
        List<Mechanic> mechanics = List.of(mechanicToFind1, mechanicToFind2, mechanicToFind3);
        when(mechanicDAO.findAvailable()).thenReturn(mechanics);
        // when
        List<Mechanic> result = mechanicService.findAvailable();
        // then
        verify(mechanicDAO).findAvailable();
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(mechanics);

    }

    @Test
    void shouldFindMechanicWithPesel() {
        // given
        Mechanic mechanicWithPesel = MechanicTestData.someMechanic();
        when(mechanicDAO.findByPesel(mechanicWithPesel.getPesel())).thenReturn(Optional.of(mechanicWithPesel));
        // when
        Mechanic result = mechanicService.findMechanic(mechanicWithPesel.getPesel());
        // then
        verify(mechanicDAO).findByPesel(mechanicWithPesel.getPesel());
        assertThat(result).isEqualTo(mechanicWithPesel);
    }

    @Test
    void shouldThrowWhenFindingNoExistingPesel() {
        // given
        Mechanic mechanic1 = MechanicTestData.someMechanic().withPesel("11111-000");
        // when, then
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> mechanicService.findMechanic(mechanic1.getPesel()), "NotFoundException was expected");
        Assertions.assertEquals(String.format("Could not find mechanic by pesel: [%s]", mechanic1.getPesel()),
                exception.getMessage());
    }
}