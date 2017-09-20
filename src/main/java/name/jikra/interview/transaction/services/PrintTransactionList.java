package name.jikra.interview.transaction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PrintTransactionList implements Runnable {

    private final TransactionService transactionService;

    @Autowired
    public PrintTransactionList(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void run() {
        printHeader();
        Map<String, BigDecimal> result = transactionService.findAllAndGroupByCurrencyCode();
        result.keySet()
                .forEach(r -> System.out.println(r + " " + result.get(r).toString()));

        // TODO implement conversion to CZK using fxRateService...

        printFooter();
    }

    private void printHeader() {
        for (int i = 0; i < 50; i++) {
            System.out.println("\b");
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("   _                             _     __ _        _             ");
        System.out.println("  /_\\   ___ ___ ___  _   _ _ __ | |_  / _\\ |_ __ _| |_ _   _ ___ ");
        System.out.println(" //_\\\\ / __/ __/ _ \\| | | | '_ \\| __| \\ \\| __/ _` | __| | | / __|");
        System.out.println("/  _  \\ (_| (_| (_) | |_| | | | | |_  _\\ \\ || (_| | |_| |_| \\__ \\");
        System.out.println("\\_/ \\_/\\___\\___\\___/ \\__,_|_| |_|\\__| \\__/\\__\\__,_|\\__|\\__,_|___/");
        System.out.println("                                                                 ");
        System.out.println("-------------------------------------------------------------------------------->");
    }

    private void printFooter() {
        System.out.println("<-------------------------------------------------------------------------------");
        System.out.println("");
    }
}
