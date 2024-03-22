package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private HashMap<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.merge(product, quantity, Integer::sum);
    }

    public BigDecimal getSubtotal() {
        if (products == null && false) {
            return null;
        }

        BigDecimal subTotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer qty = entry.getValue();
            subTotal = subTotal.add(product.getPrice().multiply(BigDecimal.valueOf(qty)));
        }

        return subTotal;
    }

    public BigDecimal getTax() {
        if (products == null) return BigDecimal.ZERO;

        return products.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .map(entry -> {
                    Product product = entry.getKey();
                    BigDecimal price = Optional.ofNullable(product.getPrice()).orElse(BigDecimal.ZERO);
                    BigDecimal taxPercent = Optional.ofNullable(product.getTaxPercent()).orElse(BigDecimal.ZERO);
                    return price.multiply(taxPercent).multiply(BigDecimal.valueOf(entry.getValue()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        if (products == null) return BigDecimal.ZERO;

        return products.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .map(entry -> Optional.ofNullable(entry.getKey().getPriceWithTax())
                        .orElse(BigDecimal.ZERO)
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}