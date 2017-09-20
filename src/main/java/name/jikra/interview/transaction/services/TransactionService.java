package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.constants.TerminalOption;
import name.jikra.interview.transaction.dao.TransactionDao;
import name.jikra.interview.transaction.entities.Transaction;
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

    private final TransactionDao dao;

    @Autowired
    public TransactionService(TransactionDao dao) {
        this.dao = dao;
    }

    public void persist(Transaction transaction) {
        dao.create(transaction);
    }

    public Map<String, BigDecimal> findAllAndGroupByCurrencyCode() {
        Map<String, BigDecimal> result = new HashMap<>();
        List<Transaction> allEntries = dao.getAllEntries();

        allEntries.forEach(t -> result.put(t.getCurrencyCode(),
                result.containsKey(t.getCurrencyCode())
                        ? result.get(t.getCurrencyCode()).add(t.getValue())
                        : t.getValue()));

        return result;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public void persistFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            System.out.println("Persisting file " + filePath + "...");
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

            Transaction transaction = new Transaction();
            transaction.setCurrencyCode(entry.substring(0, 3).toUpperCase());
            transaction.setValue(BigDecimal.valueOf(value));

            persist(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
