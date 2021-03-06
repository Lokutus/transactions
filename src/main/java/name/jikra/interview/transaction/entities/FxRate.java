package name.jikra.interview.transaction.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fxrate")
public class FxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String currencyCode;
    private BigDecimal value;

    public FxRate(final String currencyCode, final BigDecimal value) {
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public FxRate() {
        // Empty
    }

    public long getId() {
        return id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
