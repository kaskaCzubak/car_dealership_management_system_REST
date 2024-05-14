package pl.hop.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.hop.business.dao.PartDAO;
import pl.hop.domain.Part;
import pl.hop.infrastructure.database.repository.jpa.PartJpaRepository;
import pl.hop.infrastructure.database.repository.mapper.PartEntityMapper;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class PartRepository implements PartDAO {

    private final PartJpaRepository partJpaRepository;
    private final PartEntityMapper mapper;

    @Override
    public List<Part> findAll() {
        return partJpaRepository.findAll().stream()
            .map(mapper::mapFromEntity)
            .toList();
    }

    @Override
    public Optional<Part> findBySerialNumber(String serialNumber) {
        return partJpaRepository.findBySerialNumber(serialNumber)
            .map(mapper::mapFromEntity);
    }
}
