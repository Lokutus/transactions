package name.jikra.interview.transaction.constants;

import java.util.Arrays;

public enum StartOption implements WithManual {

    FILE("file", "--file=<FILEPATH>"),
    REFRESH("refresh", "--refresh=<SECONDS>"),
    CONVERSION("conversion", "--conversion(not implemented yet...)"),
    HELP("help", "--help");

    private String option;
    private String man;

    StartOption(final String option, final String man) {
        this.option = option;
        this.man = man;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String getMan() {
        return man;
    }

    public static StartOption from(final String value) {
        return Arrays.stream(values())
                .filter(v -> v.option.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(value));
    }
}
