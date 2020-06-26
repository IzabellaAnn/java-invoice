package pl.edu.agh.mwo.invoice.printer;

import java.util.List;

import pl.edu.agh.mwo.invoice.Invoice;

public interface InvoicePrinter {
    void print(Invoice invoice);
}
