package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import static org.skypro.skyshop.service.AppTest.myAppTest;

@Service
public class StorageService {

    private final Map<UUID, Product> productMap;
    private  final Map<UUID, Article> articleMap;

    // конструктор пустой, так как (разъяснения нейронки)
    // Для реального проекта обычно используют пустой конструктор с созданием new HashMap<>() внутри,
    // потому что: Проще.(контруктор IDEA можно использовать для тестов)
                    //Не нужно передавать Map при создании сервиса.
                    //Spring сам создаст сервис и вызовет пустой конструктор
    //Spring не сможет создать бин, потому что не знает, какие Map передавать.
    // Поэтому пустой конструктор обязателен, если ты используешь @Service

    public StorageService() {
        this.productMap = new HashMap<>();
        this.articleMap = new HashMap<>();
        fillTestData();
    }

    // метод возвращающий коллекцию всех статей
    public Collection<Product> getAllProduct() {
        return productMap.values();
    }

    // метод возвращающий коллекцию всех продуктов
    public Collection<Article> getAllArticle() {
        return articleMap.values();
    }
/*
    public Collection<Searchable> getAllSearchable() {
        Collection<Searchable> result = new ArrayList<>();
        result.addAll(productMap.values());   // Берёт все продукты из productMap
        result.addAll(articleMap.values());   // Берёт все статьи из articleMap.
        return result;                        // результат Объединяет их в одну коллекцию Collection<Searchable>
    }
*/

    // StreamAPI - помогла нейронка, впрочем, половину сделал она
    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(
                productMap.values().stream(),   // поток продуктов
                articleMap.values().stream()    // поток статей
        ).collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productMap.get(id));
    }

    // метод для заполнения тестовыми данными контруктора
    // я пытался перетащить весь Main из предыдущего проекта, но из этого нечего не вышло

    private void fillTestData() {
        // Добавляем продукты
        SimpleProduct simpleProduct = new SimpleProduct("Маркер", 100);
        DiscountedProduct discountedProduct = new DiscountedProduct("Карандаш", 100, 10);
        FixPriceProduct fixPriceProduct = new FixPriceProduct("Тетрадь");

        productMap.put(simpleProduct.getId(), simpleProduct);
        productMap.put(discountedProduct.getId(), discountedProduct);
        productMap.put(fixPriceProduct.getId(), fixPriceProduct);

        // Добавляем статьи
        Article article1 = new Article("Погодные условия", "12/05/ Погода дождливая....");
        Article article2 = new Article("Солнечная погода", "Сегодня солнечно...");
        Article article3 = new Article("Рисование", "Фломастер рисует среднюю линию");

        articleMap.put(article1.getId(), article1);
        articleMap.put(article2.getId(), article2);
        articleMap.put(article3.getId(), article3);
    }
}
