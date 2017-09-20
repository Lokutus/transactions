package name.jikra.interview.transaction.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@Component
public class PokusTest {

    @Autowired
    private Pokus pokus;

    @Test
    public void testRun() {
        int result = pokus.getResult();
        assertEquals(1, result);
    }


}
