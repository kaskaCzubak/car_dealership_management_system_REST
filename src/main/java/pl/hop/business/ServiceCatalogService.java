package pl.hop.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.hop.business.dao.ServiceDAO;
import pl.hop.domain.Service;
import pl.hop.domain.exception.NotFoundException;

import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceCatalogService {

    private final ServiceDAO serviceDAO;


    public Service findService(String serviceCode) {
        return serviceDAO.findByServiceCode(serviceCode)
                .orElseThrow(() -> new NotFoundException("Could not find service by service code: [%s]".formatted(serviceCode)));
    }

    public List<Service> findAll() {
        List<Service> services = serviceDAO.findAll();
        log.info("Available services: [{}]", services);
        return services;
    }
}
