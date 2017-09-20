package name.jikra.interview.transaction.services;

import name.jikra.interview.transaction.entities.FxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetFxRates implements Runnable {

    private final static String LINES_DELIMITER_PATTERN = "\\r\\n|\\n|\\r";
    private final static String VALUES_DELIMITER_PATTERN = "\\|";
    private final static int FIRST_DATA_LINE = 2;
    private final static int CURRENCY_CODE_POSITION = 3;
    private final static int VALUE_POSITION = 4;
    private final static int VALUES_EXPECTED_LENGTH = 5;

    private final FxRatesClient fxRatesClient;

    private final FxRateService fxRateService;

    @Autowired
    public GetFxRates(FxRatesClient fxRatesClient, FxRateService fxRateService) {
        this.fxRatesClient = fxRatesClient;
        this.fxRateService = fxRateService;
    }

    @Override
    public void run() {
        String fxRatesResponse = fxRatesClient.getFxRates();
        List<FxRate> fxRates = parse(fxRatesResponse);
        fxRates.forEach(fxRateService::persist);
    }

    private List<FxRate> parse(final String fxRatesResponse) {
        List<FxRate> fxRates = new ArrayList<>();

        String[] lines = fxRatesResponse.split(LINES_DELIMITER_PATTERN);
        for (int i = 0; i < lines.length; i++) {
            if (i >= FIRST_DATA_LINE) {
                String[] values = lines[i].split(VALUES_DELIMITER_PATTERN);
                if (values.length >= VALUES_EXPECTED_LENGTH) {
                    FxRate fxRate = new FxRate();
                    fxRate.setCurrencyCode(values[CURRENCY_CODE_POSITION]);
                    fxRate.setValue(BigDecimal.valueOf(Double.valueOf(values[VALUE_POSITION]
                            .replace(",", "."))));
                    fxRates.add(fxRate);
                }
            }
        }

        return fxRates;
    }
}
