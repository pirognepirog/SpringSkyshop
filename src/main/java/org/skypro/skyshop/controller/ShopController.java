package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

// Чтобы контроллер корректно работал и возвращал JSON-ответы, обязательно пометьте его аннотацией
// @RestController. Без неё ваш контроллер не будет обрабатываться как REST, и браузер не получит нужные данные

@RestController
public class ShopController {

    private final StorageService storageService; // поле для сервиса
    private final SearchService searchService;
    private final BasketService basketService; // поле для корзины

    // Конструктор (генерация IDEA)
    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProduct();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles(){
        return storageService.getAllArticle();
    }

    @GetMapping("/search")
    public Collection<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    // метод для отображения корзины
    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getBasket();
    };

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        try {
            basketService.addProductToBasket(id);
            return "Продукт успешно добавлен!";
        } catch (IllegalArgumentException e) {
            return "Ошибка " + e.getMessage();
        }
    };


}
