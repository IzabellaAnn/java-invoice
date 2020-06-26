package pl.edu.agh.mwo.invoice.printer;

import java.math.BigDecimal;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.Product;

public class ConsoleInvoicePrinter implements InvoicePrinter {
    @Override
    public void print(Invoice invoice) {
        final Integer numOfProducts = invoice.getProducts().values().stream().reduce(Integer::sum).orElse(0);

        System.out.println("--------------------------------------------");
        System.out.printf("| %40s |\n", "Faktura nr: " + invoice.getNumber());
        System.out.println("--------------------------------------------");
        invoice.getProducts().forEach((product, quantity) ->
                System.out.printf("| %20s | %7s | %7s |\n",
                product.getName(), quantity, calculateTotalPrice(product, quantity)));
        System.out.println("--------------------------------------------");
        System.out.printf("| %40s |\n", "Liczba pozycji: " + numOfProducts);
        System.out.println("--------------------------------------------");
    }

    private Object calculateTotalPrice(
            Product product,
            Integer quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
