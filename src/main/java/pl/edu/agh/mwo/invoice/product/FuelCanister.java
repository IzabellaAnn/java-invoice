package pl.edu.agh.mwo.invoice.product;

import pl.edu.agh.mwo.invoice.Invoice;
import java.math.BigDecimal;

public class FuelCanister extends Invoice.ExcisableProduct {
    private static final BigDecimal EXCISE_DUTY = new BigDecimal("5.56");

    // Dzień Teściowej
    public FuelCanister(String name, BigDecimal price) {
        super(name, price, BigDecimal.ZERO, EXCISE_DUTY);
    }
}