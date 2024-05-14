package pl.hop.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.ServiceDAO;
import pl.hop.domain.Service;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.ServiceTestData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceCatalogServiceTest {

    @InjectMocks
    private ServiceCatalogService serviceCatalogService;

    @Mock
    private ServiceDAO serviceDAO;

    @Test
    void shouldFindAllServices() {
        //give
        var serviceToFind1 = ServiceTestData.someService();
        var serviceToFind2 = ServiceTestData.someService().withServiceCode("67111-111");
        var serviceToFind3 = ServiceTestData.someService().withServiceCode("82111-111");
        List<Service> services = List.of(serviceToFind1, serviceToFind2, serviceToFind3);
        when(serviceDAO.findAll()).thenReturn(services);
        // when
        var result = serviceCatalogService.findAll();
        // then
        verify(serviceDAO).findAll();
        assertThat(result).isEqualTo(services);
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("Should find service by service code successfully")
    void shouldFindServiceWithServiceCode() {
        // given
        Service serviceWithServiceCode = ServiceTestData.someService();
        when(serviceDAO.findByServiceCode(serviceWithServiceCode.getServiceCode())).thenReturn(Optional.of(serviceWithServiceCode));
        // when
        Service result = serviceCatalogService.findService(serviceWithServiceCode.getServiceCode());
        // then
        verify(serviceDAO).findByServiceCode(serviceWithServiceCode.getServiceCode());
        assertThat(result).isEqualTo(serviceWithServiceCode);
    }

    @Test
    void shouldThrowWhenFindingNoExistingServiceCode() {
        // given
        Service serviceNonServiceCode = ServiceTestData.someService().withServiceCode("11111-000");
        // when, then
        assertThatThrownBy(() -> { throw new NotFoundException(
                String.format("Could not find service by service code: [%s]", serviceNonServiceCode.getServiceCode())); })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("Could not find service by service code: [%s]", serviceNonServiceCode.getServiceCode()));

    }

}