package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.dao.FxRateDao;
import name.jikra.interview.transaction.entities.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FxRateService {

    private final FxRateDao dao;

    @Autowired
    public FxRateService(FxRateDao dao) {
        this.dao = dao;
    }

    public void persist(FxRate fxRate) {
        dao.create(fxRate);
    }

    public FxRate findByCurrencyCode() {
        // TODO implement findByCurrencyCode...
        return null;
    }

    public List<FxRate> findAll() {
        return dao.getAllEntries();
    }

    public void deleteAll() {
        dao.deleteAll();
    }
}
