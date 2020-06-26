package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.printer.ConsoleInvoicePrinter;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class ConsoleInvoicePrinterTest {

    private ConsoleInvoicePrinter printer;

    @Before
    public void setUp() throws Exception {
        printer = new ConsoleInvoicePrinter();
    }

    @Test
    public void should_print_invoice() throws Exception {
        final Invoice invoice = invoiceFixture();
        printer.print(invoice);
    }

    private Invoice invoiceFixture() {
        final Invoice invoice = new Invoice();
        invoice.addProduct(new TaxFreeProduct("książka", new BigDecimal("39.9")));
        invoice.addProduct(new OtherProduct("żarówka", new BigDecimal("10")));
        invoice.addProduct(new OtherProduct("guma do żucia", new BigDecimal("3.5")));
        invoice.addProduct(new DairyProduct("mleko", new BigDecimal("2.95")));
        return invoice;
    }

}
