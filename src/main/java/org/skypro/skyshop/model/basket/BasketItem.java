package org.skypro.skyshop.model.basket;


import org.skypro.skyshop.model.product.Product;

import java.util.Objects;

// класс представляет один товар в корзине с его количеством
public final class BasketItem {

    private final Product product;
    private final int quantity;

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BasketItem that = (BasketItem) o;
        return quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = " + (product.getPriceProduct() * quantity) + " руб.";
    }


}
