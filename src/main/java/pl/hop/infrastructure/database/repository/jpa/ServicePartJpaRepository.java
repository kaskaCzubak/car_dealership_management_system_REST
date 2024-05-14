package pl.hop.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hop.infrastructure.database.entity.ServicePartEntity;

@Repository
public interface ServicePartJpaRepository extends JpaRepository<ServicePartEntity, Integer> {

}
