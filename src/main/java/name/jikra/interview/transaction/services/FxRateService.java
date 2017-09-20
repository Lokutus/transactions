package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.entities.FxRate;
import name.jikra.interview.transaction.repositories.FxRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FxRateService {

    private final FxRateRepository repository;

    @Autowired
    public FxRateService(FxRateRepository repository) {
        this.repository = repository;
    }

    public void persist(FxRate fxRate) {
        repository.save(fxRate);
    }

    public FxRate findByCurrencyCode() {
        // TODO implement findByCurrencyCode...
        return null;
    }

    public List<FxRate> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
