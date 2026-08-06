package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        // Проверяем существование товара
        storageService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + id + " не найден"));

        // Добавляем в корзину
        productBasket.addProduct(id);
    }

    public Map<UUID, Integer> getBasketContents() {
        return productBasket.getProducts();
    }

    // метод возвращает корзину для пользователя
    public UserBasket getBasket() {
        return productBasket.getUserBasket(storageService);
    }

}
