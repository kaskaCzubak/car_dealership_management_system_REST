package pl.hop.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.hop.business.dao.CarToBuyDAO;
import pl.hop.domain.CarToBuy;
import pl.hop.infrastructure.database.repository.jpa.CarToBuyJpaRepository;
import pl.hop.infrastructure.database.repository.mapper.CarToBuyEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarToBuyRepository implements CarToBuyDAO {

    private final CarToBuyJpaRepository carToBuyJpaRepository;
    private final CarToBuyEntityMapper carToBuyEntityMapper;

    @Override
    public List<CarToBuy> findAvailable() {
        return carToBuyJpaRepository.findAvailableCars().stream()
            .map(carToBuyEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Optional<CarToBuy> findCarToBuyByVin(String vin) {
        return carToBuyJpaRepository.findByVin(vin)
            .map(carToBuyEntityMapper::mapFromEntity);
    }
}
