package pl.hop.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.SalesmanDAO;
import pl.hop.domain.Salesman;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.SalesmanTestData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesmanServiceTest {

    @InjectMocks
    private SalesmanService salesmanService;
    @Mock
    private SalesmanDAO salesmanDAO;

    @Test
    void shouldFindAvailableSalesmen() {
        //give
        Salesman salesmanToFind1 = SalesmanTestData.someSalesman();
        Salesman salesmanToFind2 = SalesmanTestData.someSalesman().withPesel("67111001111");
        Salesman salesmanToFind3 = SalesmanTestData.someSalesman().withPesel("82111001111");
        List<Salesman> salesmen = List.of(salesmanToFind1, salesmanToFind2, salesmanToFind3);
        when(salesmanDAO.findAvailable()).thenReturn(salesmen);
        // when
        List<Salesman> result = salesmanService.findAvailable();
        // then
        verify(salesmanDAO).findAvailable();
        assertThat(result).isEqualTo(salesmen);
        assertThat(result).hasSize(3);

    }


    @Test
    void shouldFindSalesmanWithPesel() {
        // given
        Salesman salesmanWithPesel = SalesmanTestData.someSalesman();
        when(salesmanDAO.findByPesel(salesmanWithPesel.getPesel())).thenReturn(Optional.of(salesmanWithPesel));
        // when
        Salesman result = salesmanService.findSalesman(salesmanWithPesel.getPesel());
        // then
        verify(salesmanDAO).findByPesel(salesmanWithPesel.getPesel());
        assertThat(result).isEqualTo(salesmanWithPesel);
    }

    @Test
    void shouldThrowWhenFindingNoExistingPesel() {
        // given
        Salesman salesmanNonPesel = SalesmanTestData.someSalesman();
        // when, then

        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> salesmanService.findSalesman(salesmanNonPesel.getPesel()), "NotFoundException was expected");
        Assertions.assertEquals(String.format("Could not find salesman by pesel: [%s]", salesmanNonPesel.getPesel()),
                exception.getMessage());

        assertThatThrownBy(() -> { throw new NotFoundException(
                String.format("Could not find salesman by pesel: [%s]", salesmanNonPesel.getPesel())); })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Could not find salesman by pesel: [%s]", salesmanNonPesel.getPesel()));

    }
}