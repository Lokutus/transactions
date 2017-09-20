package name.jikra.interview.transaction.repositories;

import name.jikra.interview.transaction.entities.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FxRateRepository extends JpaRepository<FxRate, Long> {
}
