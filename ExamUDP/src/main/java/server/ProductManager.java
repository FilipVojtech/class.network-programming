package server;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductManager implements IProductManager {
    private final Map<Integer, Product> products;

    public ProductManager() {
        products = new HashMap<>();
    }

    @Override
    public boolean contains(int id) {
        return products.containsKey(id);
    }

    @Override
    public boolean add(@NonNull int id, @NonNull String name, @NonNull String category, double unitPrice) {
        var product = new Product(id, name, category, unitPrice);

        return add(product);
    }

    @Override
    public boolean add(Product product) {
        if (products.containsKey(product.getId())) return false;
        products.put(product.getId(), product);
        return true;
    }

    @Override
    public Product delete(int id) {
        if (!products.containsKey(id)) return null;
        return products.remove(id);
    }

    @Override
    public Product get(int id) {
        return products.get(id);
    }

    @Override
    public Product[] search(@NonNull String category) {
        List<Product> results = new LinkedList<>();
        products.forEach((key, product) -> {
            if (product.getCategory().equalsIgnoreCase(category)) results.add(product);
        });

        Product[] resultsAsArr = new Product[results.size()];
        results.toArray(resultsAsArr);
        return resultsAsArr;
    }

}
