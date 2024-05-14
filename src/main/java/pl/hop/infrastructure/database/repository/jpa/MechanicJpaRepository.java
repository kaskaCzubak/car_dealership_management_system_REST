package pl.hop.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hop.infrastructure.database.entity.MechanicEntity;

import java.util.Optional;

@Repository
public interface MechanicJpaRepository extends JpaRepository<MechanicEntity, Integer> {

    Optional<MechanicEntity> findByPesel(String pesel);
}