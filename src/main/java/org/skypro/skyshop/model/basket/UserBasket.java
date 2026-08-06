package org.skypro.skyshop.model.basket;


import java.util.Collections;
import java.util.List;

// класс представляет корзину для пользователя с общим списком товаров и итоговой стоимостью
public class UserBasket {
    private final List<BasketItem> items;
    private final int total;

    public UserBasket(List<BasketItem> items) {
        this.items = Collections.unmodifiableList(items); // Защита от изменений
        this.total = items.stream()
                .mapToInt(item -> item.getProduct().getPriceProduct() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Корзина: " + items + ", Итого: " + total + " руб.";
    }
}
