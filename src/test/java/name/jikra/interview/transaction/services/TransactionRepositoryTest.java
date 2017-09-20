package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.entities.Transaction;
import name.jikra.interview.transaction.repositories.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    @Transactional
    public void testSaveAndRead() {
        repository.save(new Transaction("USD", BigDecimal.valueOf(333)));
        assertTrue(repository.findAll().size() == 1);
    }

    @Test
    @Transactional
    public void testServiceSaveAndReadAndDeleteAll() {
        repository.save(new Transaction("USD", BigDecimal.valueOf(333)));
        assertTrue(repository.findAll().size() == 1);
        repository.deleteAll();
        assertTrue(repository.findAll().size() == 0);
    }
}
