package name.jikra.interview.transaction.dao;

import name.jikra.interview.transaction.entities.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class TransactionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Transaction transaction) {
        entityManager.persist(transaction);
    }

    public void deleteAll() {
        int result = entityManager
                .createNativeQuery("DELETE FROM TRANSACTION", Transaction.class)
                .executeUpdate();
    }

    public List<Transaction> getAllEntries() {
        CriteriaQuery<Transaction> query = entityManager
                .getCriteriaBuilder()
                .createQuery(Transaction.class);
        return entityManager
                .createQuery(query.select(query.from(Transaction.class)))
                .getResultList();
    }
}
