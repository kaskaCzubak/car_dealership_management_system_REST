package pl.hop.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.PartDAO;
import pl.hop.domain.Part;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.PartTestData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartCatalogServiceTest {

    @InjectMocks
    private PartCatalogService partCatalogService;

    @Mock
    private PartDAO partDAO;

    @Test
    void shouldFindAllParts() {
        //give
        Part partToFind1 = PartTestData.somePart();
        Part partToFind2 = PartTestData.somePart().withSerialNumber("67111-1111");
        Part partToFind3 = PartTestData.somePart().withSerialNumber("82111-1111");
        List<Part> parts = List.of(partToFind1, partToFind2, partToFind3);
        when(partDAO.findAll()).thenReturn(parts);
        // when
        List<Part> result = partCatalogService.findAll();
        // then
        verify(partDAO).findAll();
        assertThat(result).isEqualTo(parts);
        assertThat(result).hasSize(3);

    }
    @Test
    void shouldFindPartWithSerialNumber() {
        // given
        Part partWithSerialNumber = PartTestData.somePart();
        when(partDAO.findBySerialNumber(partWithSerialNumber.getSerialNumber())).thenReturn(Optional.of(partWithSerialNumber));
        // when
        Part result = partCatalogService.findPart(partWithSerialNumber.getSerialNumber());
        // then
        verify(partDAO).findBySerialNumber(partWithSerialNumber.getSerialNumber());
        assertThat(result).isEqualTo(partWithSerialNumber);
    }

    @Test
    void shouldThrowWhenFindingNoExistingPartSerialNumber() {
        // given
        Part partWithNonPartSerialNumber = PartTestData.somePart();
        // when, then
        assertThatThrownBy(() -> { throw new NotFoundException(
                String.format("Could not find part by part serial number: [%s]", partWithNonPartSerialNumber.getSerialNumber())); })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Could not find part by part serial number: [%s]", partWithNonPartSerialNumber.getSerialNumber()));
    }
}