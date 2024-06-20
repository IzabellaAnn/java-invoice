package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.mwo.invoice.product.*;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTotal()));
    }

    // 1. Drukowanie

    @Test
    public void testInvoicePrintContainsAnInvoiceNumber() {
        Invoice invoice = new Invoice();
        String invoiceDetails = invoice.printInvoice();
        Assert.assertTrue("Invoice printout should contain 'Invoice Number:'", invoiceDetails.contains("Invoice Number:"));
    }

    @Test
    public void testInvoiceHasDifferentNumbers() {
        int number1 = new Invoice().getInvoiceNumber();
        int number2 = new Invoice().getInvoiceNumber();
        Assert.assertThat(number2, Matchers.greaterThan(number1));
    }

    @Test
    public void testPrintInvoice() {
        Invoice invoice = new Invoice();
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("2.50")), 10);
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("3.75")), 5);

        System.out.println(invoice.printInvoice());
    }

    // 2. Duplikaty produktów

    @Test
    public void testPrintInvoiceWithDuplicates() {
        Product bread = new TaxFreeProduct("Chleb", new BigDecimal("3.75"));
        invoice.addProduct(bread, 5);
        invoice.addProduct(bread, 5);
        String expectedOutput = "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
                "Chleb, Qty: 10, Price: 3.75\n" +
                "Number of Items: 1";
        Assert.assertEquals(expectedOutput, invoice.printInvoice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    // 3. VAT to nie wszystko

    @Test
    public void validateBottleOfWinePriceWithExciseAndTax() {
        Product wine = new BottleOfWine("Wine", new BigDecimal("100.00"), new BigDecimal("0.20"));
        invoice.addProduct(wine, 1);
        BigDecimal calculatedPriceWithTax = wine.getPriceWithTax().setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal expectedPriceWithTax = new BigDecimal("125.56").setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals("The calculated price should reflect both VAT and excise duty.", expectedPriceWithTax, calculatedPriceWithTax);
    }

    @Test
    public void verifyFuelCanisterPriceWithExciseDuty() {
        Product fuel = new FuelCanister("Diesel Fuel", new BigDecimal("200.00"));
        invoice.addProduct(fuel, 1);
        BigDecimal computedPriceWithExcise = fuel.getPriceWithTax().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal expectedPriceWithExcise = new BigDecimal("205.56").setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals("The calculated price should include only the excise duty.", expectedPriceWithExcise, computedPriceWithExcise);
    }
}
