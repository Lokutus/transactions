package name.jikra.interview.transaction.dao;

import name.jikra.interview.transaction.entities.FxRate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class FxRateDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(FxRate fxRate) {
        entityManager.persist(fxRate);
    }

    public void deleteAll() {
        int result = entityManager
                .createNativeQuery("DELETE FROM FxRate", FxRate.class)
                .executeUpdate();
    }

    public List<FxRate> getAllEntries() {
        CriteriaQuery<FxRate> query = entityManager
                .getCriteriaBuilder()
                .createQuery(FxRate.class);
        return entityManager
                .createQuery(query.select(query.from(FxRate.class)))
                .getResultList();
    }
}
