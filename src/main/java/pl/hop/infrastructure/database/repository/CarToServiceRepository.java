package pl.hop.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.hop.business.dao.CarToServiceDAO;
import pl.hop.domain.CarHistory;
import pl.hop.domain.CarToService;
import pl.hop.infrastructure.database.entity.CarToServiceEntity;
import pl.hop.infrastructure.database.repository.jpa.CarToServiceJpaRepository;
import pl.hop.infrastructure.database.repository.mapper.CarToServiceEntityMapper;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarToServiceRepository implements CarToServiceDAO {

    private final CarToServiceJpaRepository carToServiceJpaRepository;
    private final CarToServiceEntityMapper carToServiceEntityMapper;

    @Override
    public List<CarToService> findAll() {
        return carToServiceJpaRepository.findAll().stream()
            .map(carToServiceEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Optional<CarToService> findCarToServiceByVin(String vin) {
        return carToServiceJpaRepository.findOptionalByVin(vin)
            .map(carToServiceEntityMapper::mapFromEntity);
    }

    @Override
    public CarToService saveCarToService(CarToService car) {
        CarToServiceEntity toSave = carToServiceEntityMapper.mapToEntity(car);
        CarToServiceEntity saved = carToServiceJpaRepository.save(toSave);
        return carToServiceEntityMapper.mapFromEntity(saved);
    }

    @Override
    public CarHistory findCarHistoryByVin(String vin) {
        CarToServiceEntity entity = carToServiceJpaRepository.findByVin(vin);
        return carToServiceEntityMapper.mapFromEntity(vin, entity);
    }
}
