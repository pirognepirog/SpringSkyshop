package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class ProductBasket {

    //пустой HashMap, чтобы не было NullPointerException
    private final Map<UUID, Integer> products = new HashMap<>();

    // Метод добавления продукта в корзину (принимает и не возвращает)
    public void addProduct(UUID prodoctId) {
        products.put(prodoctId, products.getOrDefault(prodoctId,0)+1);
        // Если товар уже есть в корзине, увеличиваем количество на 1
        // Если нет — кладем его с количеством 1
    }

    //Метод получения всех продуктов, которые сейчас есть в корзине
    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(products); // .unmodifiableMap - создает обертку только для чтения
    }

    // метод очистки корзины
    public void clearBasket() {
        products.clear();
    }

    // метод преобразует корзину в UserBasket для отображения пользователю
    public UserBasket getUserBasket(StorageService storageService) {
        List<BasketItem> items = new ArrayList<>();

        for (Map.Entry<UUID, Integer> entry : products.entrySet()) {
            UUID productId = entry.getKey();
            int quantity = entry.getValue();

            // Получаем продукт из StorageService
            Optional<Product> productOptional = storageService.getProductById(productId);

            // Если продукт найден, добавляем его в список
            productOptional.ifPresent(product ->
                    items.add(new BasketItem(product, quantity))
            );
        }

        return new UserBasket(items);
    }

}
