package server;

import lombok.NonNull;

public interface IProductManager {
    boolean contains(int id);

    boolean add(@NonNull int id, @NonNull String name, @NonNull String category, double unitPrice);

    boolean add(Product product);

    Product delete(int id);

    Product get(int id);

    Product[] search(@NonNull String category);
}
