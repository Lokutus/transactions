package name.jikra.interview.transaction.components;

import name.jikra.interview.transaction.constants.Constants;
import name.jikra.interview.transaction.constants.StartOption;
import name.jikra.interview.transaction.events.Signal;
import name.jikra.interview.transaction.events.SignalType;
import name.jikra.interview.transaction.services.GetFxRates;
import name.jikra.interview.transaction.services.PrintTransactionList;
import name.jikra.interview.transaction.services.TransactionService;
import name.jikra.interview.transaction.util.StartOptionsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionsApplicationRunner implements ApplicationRunner {

    private final TerminalListener terminalListener;

    private final StartOptionsHandler startOptions;

    private final TransactionService transactionService;

    private final PrintTransactionList printTransactionList;

    private final GetFxRates getFxRates;

    private ScheduledExecutorService executor;

    @Autowired
    public TransactionsApplicationRunner(
            TerminalListener terminalListener,
            StartOptionsHandler startOptions,
            TransactionService transactionService,
            PrintTransactionList printTransactionList,
            GetFxRates getFxRates) {
        this.terminalListener = terminalListener;
        this.startOptions = startOptions;
        this.transactionService = transactionService;
        this.printTransactionList = printTransactionList;
        this.getFxRates = getFxRates;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        // Load all CLI options
        applicationArguments.getOptionNames().stream()
                .map(StartOption::from)
                .forEach(s -> startOptions.add(s, applicationArguments.getOptionValues(s.getOption())));

        // Handle potential --help option and quit application if present
        if (startOptions.isHelp()) {
            startOptions.printHelp();
            return;
        }

        // Persist potential entries from files if --file args are present
        startOptions.getFiles().ifPresent(transactionService::persistFiles);

        // Start async tasks
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(printTransactionList, 0, getRefreshRate(), TimeUnit.SECONDS);

        // Start GetFxRates service to download FX Rates from ČNB rest service to be able to convert values to CZK
        if (startOptions.isConversion()) {
            executor.scheduleAtFixedRate(getFxRates, 0, 30, TimeUnit.MINUTES);
        }

        // Start listening terminal inputs
        terminalListener.listen();
    }

    @EventListener
    public void processSignal(Signal signal) {
        if (SignalType.QUIT.equals(signal.getSource())) {
            executor.shutdown();
        }
        if (SignalType.CLEAN.equals(signal.getSource())) {
            transactionService.deleteAll();
        }
    }

    private int getRefreshRate() {
        return startOptions.getRefreshRate()
                .orElse(Constants.DEFAULT_REFRESH_RATE);
    }
}
