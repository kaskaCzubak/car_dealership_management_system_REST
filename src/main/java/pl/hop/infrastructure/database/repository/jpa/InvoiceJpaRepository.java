package pl.hop.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hop.infrastructure.database.entity.InvoiceEntity;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Integer> {


}
