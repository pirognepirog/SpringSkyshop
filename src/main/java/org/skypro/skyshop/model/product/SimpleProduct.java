package org.skypro.skyshop.model.product;

import java.util.Objects;

public class SimpleProduct extends Product {

    private int priceProduct;

    public SimpleProduct(String nameProduct, int priceProduct) {
        super(nameProduct);
        if (priceProduct < 1) {
            throw new IllegalArgumentException("Цена должна быть больше 0! Для " + nameProduct +
                    " указана цена = " + priceProduct);
        }
        this.priceProduct = priceProduct;
    }

    @Override
    public boolean isSpecial() {
        return false;  // обычный товар
    }

    @Override
    public int getPriceProduct() {
        return priceProduct;
    }

    @Override
    public String getSearchTerm() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SimpleProduct that = (SimpleProduct) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
