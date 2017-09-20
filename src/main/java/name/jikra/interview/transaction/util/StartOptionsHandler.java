package name.jikra.interview.transaction.util;

import name.jikra.interview.transaction.constants.StartOption;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StartOptionsHandler {

    private final Map<StartOption, List<String>> options = new HashMap<>();

    public void add(StartOption startOption, List<String> startOptionValues) {
        options.put(startOption, startOptionValues);
    }

    public boolean isHelp() {
        return options.containsKey(StartOption.HELP);
    }

    public boolean isRefresh() {
        return options.containsKey(StartOption.REFRESH)
                && !options.get(StartOption.REFRESH).isEmpty();
    }

    public boolean isFile() {
        return options.containsKey(StartOption.FILE)
                && !options.get(StartOption.FILE).isEmpty();
    }

    public boolean isConversion() {
        return options.containsKey(StartOption.CONVERSION)
                && !options.get(StartOption.CONVERSION).isEmpty();
    }

    public void printHelp() {
        System.out.println("Following options are available:");
        Arrays.stream(StartOption.values()).forEach(s -> System.out.println(" - " + s.getMan()));
    }

    public Optional<Integer> getRefreshRate() {
        if (isRefresh()) {
            List<String> rates = options.get(StartOption.REFRESH);

            if (!rates.isEmpty()) {
                try {
                    return Optional.of(Integer.parseInt(rates.get(0)));
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    public Optional<List<String>> getFiles() {
        if (isFile()) {
            List<String> files = options.get(StartOption.FILE);
            if (!files.isEmpty()) {
                return Optional.of(files);
            }
        }
        return Optional.empty();
    }
}
