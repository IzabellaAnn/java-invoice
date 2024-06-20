package pl.edu.agh.mwo.invoice.product;

import pl.edu.agh.mwo.invoice.Invoice;

import java.math.BigDecimal;

public class BottleOfWine extends Invoice.ExcisableProduct {
    private static final BigDecimal EXCISE_DUTY = new BigDecimal("5.56");

    public BottleOfWine(String name, BigDecimal price, BigDecimal taxPercent) {
        super(name, price, taxPercent, EXCISE_DUTY);
    }
}
