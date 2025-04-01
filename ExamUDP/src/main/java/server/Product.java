package server;

import common.InventoryUtils;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
public class Product {
    @EqualsAndHashCode.Exclude
    public static final Set<String> validCategories = Set.of("food", "electronics", "media");

    private int id;
    @NonNull
    private String name;
    @NonNull
    private String category;
    @NonNull
    private double unitPrice;

    public void setCategory(@NonNull String category) {
        category = category.toLowerCase();
        if (!validCategories.contains(category)) return;
        this.category = category;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0.50) return;

        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id).append(InventoryUtils.DELIMITER);
        sb.append(name).append(InventoryUtils.DELIMITER);
        sb.append(category).append(InventoryUtils.DELIMITER);
        sb.append(unitPrice);
        return sb.toString();
    }
}
