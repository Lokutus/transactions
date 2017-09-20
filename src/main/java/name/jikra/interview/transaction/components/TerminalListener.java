package name.jikra.interview.transaction.components;

import name.jikra.interview.transaction.constants.TerminalOption;
import name.jikra.interview.transaction.events.Signal;
import name.jikra.interview.transaction.events.SignalType;
import name.jikra.interview.transaction.services.TransactionService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TerminalListener {

    private boolean isAlive = Boolean.TRUE;

    private final ApplicationEventPublisher publisher;

    private final TransactionService transactionService;

    @Autowired
    public TerminalListener(ApplicationEventPublisher publisher, TransactionService transactionService) {
        this.publisher = publisher;
        this.transactionService = transactionService;
    }

    void listen() {
        TextIO textIO = TextIoFactory.getTextIO();

        while (isAlive) {
            String entry = textIO.newStringInputReader()
                    .read("");

            TextTerminal terminal = textIO.getTextTerminal();

            if (TerminalOption.QUIT.matches(entry)) {
                terminal.println("\nQuitting...");
                quit();
            } else if (TerminalOption.ENTRY.matches(entry)) {
                transactionService.persist(entry);
                terminal.println("\nEntry was saved: " + entry);
            } else if (TerminalOption.CLEAN.matches(entry)) {
                terminal.println("\nCleaning database...");
                clean();
            } else {
                terminal.println("\nEntry not valid. Valid entries are as follows:");
                Arrays.stream(TerminalOption.values()).forEach(s -> terminal.println(" - " + s.getMan()));
            }
        }
    }

    private void quit() {
        isAlive = Boolean.FALSE;
        publisher.publishEvent(new Signal(SignalType.QUIT));
    }

    private void clean() {
        publisher.publishEvent(new Signal(SignalType.CLEAN));
    }
}
