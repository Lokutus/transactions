package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.constants.TerminalOption;
import name.jikra.interview.transaction.entities.Transaction;
import name.jikra.interview.transaction.repositories.TransactionRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void persist(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Map<String, BigDecimal> findAllAndGroupByCurrencyCode() {
        Map<String, BigDecimal> result = new HashMap<>();
        List<Transaction> allEntries = transactionRepository.findAll();

        allEntries.forEach(t -> result.put(t.getCurrencyCode(),
                result.containsKey(t.getCurrencyCode())
                        ? result.get(t.getCurrencyCode()).add(t.getValue())
                        : t.getValue()));

        return result;
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    public void persistFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            File file = new File(filePath);

            try {
                List<String> lines = FileUtils.readLines(file, "UTF-8");
                lines.stream()
                        .filter(l -> TerminalOption.ENTRY.matches(l.trim()))
                        .forEach(this::persist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void persist(String entry) {
        try {
            Double value = Double.valueOf(entry.substring(4).replace(",", "."));
            persist(new Transaction(
                    entry.substring(0, 3).toUpperCase(),
                    BigDecimal.valueOf(value)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
