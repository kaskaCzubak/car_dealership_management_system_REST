package pl.hop.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.hop.business.dao.ServiceRequestProcessingDAO;
import pl.hop.domain.CarServiceRequest;
import pl.hop.domain.ServiceMechanic;
import pl.hop.domain.ServicePart;
import pl.hop.infrastructure.database.entity.CarServiceRequestEntity;
import pl.hop.infrastructure.database.entity.PartEntity;
import pl.hop.infrastructure.database.entity.ServiceMechanicEntity;
import pl.hop.infrastructure.database.entity.ServicePartEntity;
import pl.hop.infrastructure.database.repository.jpa.CarServiceRequestJpaRepository;
import pl.hop.infrastructure.database.repository.jpa.PartJpaRepository;
import pl.hop.infrastructure.database.repository.jpa.ServiceMechanicJpaRepository;
import pl.hop.infrastructure.database.repository.jpa.ServicePartJpaRepository;
import pl.hop.infrastructure.database.repository.mapper.ServiceMechanicEntityMapper;
import pl.hop.infrastructure.database.repository.mapper.ServicePartEntityMapper;

import java.util.Objects;


@Repository
@AllArgsConstructor
public class ServiceRequestProcessingRepository implements ServiceRequestProcessingDAO {

    private final ServiceMechanicJpaRepository serviceMechanicJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final PartJpaRepository partJpaRepository;
    private final ServicePartJpaRepository servicePartJpaRepository;
    private final ServiceMechanicEntityMapper serviceMechanicEntityMapper;
    private final ServicePartEntityMapper servicePartEntityMapper;

    @Override
    @Transactional
    public void process(
        CarServiceRequest serviceRequest,
        ServiceMechanic serviceMechanic
    ) {
        ServiceMechanicEntity serviceMechanicEntity = serviceMechanicEntityMapper.mapToEntity(serviceMechanic);
        serviceMechanicJpaRepository.saveAndFlush(serviceMechanicEntity);
        if (Objects.nonNull(serviceRequest.getCompletedDateTime())) {
            CarServiceRequestEntity carServiceRequestEntity = carServiceRequestJpaRepository
                .findById(serviceRequest.getCarServiceRequestId())
                .orElseThrow();
            carServiceRequestEntity.setCompletedDateTime(serviceRequest.getCompletedDateTime());
            carServiceRequestJpaRepository.saveAndFlush(carServiceRequestEntity);
        }
    }

    @Override
    @Transactional
    public void process(
        CarServiceRequest serviceRequest,
        ServiceMechanic serviceMechanic,
        ServicePart servicePart
    ) {
        PartEntity partEntity = partJpaRepository.findById(servicePart.getPart().getPartId()).orElseThrow();
        ServicePartEntity servicePartEntity = servicePartEntityMapper.mapToEntity(servicePart);
        servicePartEntity.setPart(partEntity);
        servicePartJpaRepository.saveAndFlush(servicePartEntity);
        process(serviceRequest, serviceMechanic);
    }
}
