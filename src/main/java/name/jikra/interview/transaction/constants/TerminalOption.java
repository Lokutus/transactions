package name.jikra.interview.transaction.constants;

import java.util.regex.Pattern;

public enum TerminalOption implements WithManual {

    ENTRY("^[a-zA-Z]{3} -?\\d{1,}((\\.|,)\\d{1,2})?$", "Currency code followed by space and positive or negative number, eg CZK 121, USD -300, EUR 210.50 ...etc."),
    QUIT("^(q|quit)$", "'q' or 'quit' to quit the application."),
    CLEAN("^(c|clean)$", "'c' or 'clean' to delete all records from database.");

    private String pattern;
    private String man;

    TerminalOption(String pattern, String man) {
        this.pattern = pattern;
        this.man = man;
    }

    @Override
    public String getMan() {
        return man;
    }

    public boolean matches(String value) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
                .matcher(value)
                .matches();
    }
}
