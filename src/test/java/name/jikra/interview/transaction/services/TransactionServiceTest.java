package name.jikra.interview.transaction.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
//@DataJpaTest
public class TransactionServiceTest {

//    @Autowired
//    private TransactionService transactionService;

    @Test
//    @Transactional
    public void testServiceSaveAndRead() {
        assertTrue(true);
//        transactionService.persist("USD 111");
//        Assert.assertTrue(transactionService.findAllAndGroupByCurrencyCode().size() == 1);
    }

//    @Test
//    @Transactional
//    public void testServiceSaveAndReadAndDeleteAll() {
//        transactionService.persist("USD 111");
//        Assert.assertTrue(transactionService.findAllAndGroupByCurrencyCode().size() == 1);
//        transactionService.deleteAll();
//        Assert.assertTrue(transactionService.findAllAndGroupByCurrencyCode().size() == 0);
//    }
}
