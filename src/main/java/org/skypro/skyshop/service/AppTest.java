package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.article.BestResultNotFound;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.ProductBasket;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchEngine;
import org.skypro.skyshop.model.search.Searchable;

import java.util.HashSet;
import java.util.TreeSet;

public class AppTest {

    private static void myAppTest(){
        //Демонстрация

        // ProductBasket basket = new ProductBasket(3);
        ProductBasket basket = new ProductBasket();

        System.out.println("===Добавление продукта в корзину.===");
        basket.addProduсtInBasket("фломастер",50);
        basket.addProduсtInBasket("карандашь",25);
        basket.addProduсtInBasket("маркер",150);

        System.out.println("===Печать содержимого корзины с несколькими товарами и Получение стоимости корзины с несколькими товарами.===");
        basket.printBacket();

        System.out.println("===Поиск товара, который есть в корзине.===");
        basket.verificationBacket("фломастер");

        System.out.println("===Поиск товара, которого нет в корзине.===");
        basket.addProduсtInBasket("тетрадь",70);
        basket.verificationBacket("тетрадь");

        System.out.println("===Очистка корзины и Печать содержимого пустой корзины и Получение стоимости пустой корзины.===");
        basket.clearBacket();
        basket.printBacket();

        System.out.println("===Поиск товара по имени в пустой корзине.===");
        basket.verificationBacket("фломастер");


        System.out.println();
        System.out.println("===  Main для второго задания  ===");// Main для второго задания

        SimpleProduct simpleProduct = new SimpleProduct("Маркер",100);
        //<имя продукта>: <стоимость>
        System.out.println(simpleProduct);
        //<имя продукта со скидкой>: <стоимость> (<скидка>%)
        DiscountedProduct discountedProduct = new DiscountedProduct("Карандаш",100,10);
        System.out.println(discountedProduct);
        //<имя продукта c фиксированной ценой>: Фиксированная цена <значение константы фиксированной цены>
        FixPriceProduct fixPrice = new FixPriceProduct("Тетрадь");
        System.out.println(fixPrice);

        // Создаём корзину и добавляем товары
        ProductBasket basketHw2 = new ProductBasket();
        basketHw2.addProduсtInBasket(simpleProduct);
        basketHw2.addProduсtInBasket(discountedProduct);
        basketHw2.addProduсtInBasket(fixPrice);

        //Итого: <общая стоимость корзины>
        basketHw2.printBacket();
        //Специальных товаров: <Количество специальных товаров>
        System.out.println("Специальных товаров: " + basketHw2.getSpecialProductCount());

        testSearchEngine();

        System.out.println();
        System.out.println("===  Main для третьего задания  ===");// Main для третьего задания

        System.out.println("===Добавление продукта в корзину.===");

        try {
            basket.addProduсtInBasket("фломастер",-50);
        }catch (IllegalArgumentException e) {
            System.out.println("Товар не создан: " + e);
        }

        try {
            basket.addProduсtInBasket(" ",50);
        }catch (IllegalArgumentException e) {
            System.out.println("Товар не создан: " + e);
        }

        try {
            DiscountedProduct discountedProductHW3 = new DiscountedProduct("Карандаш",100,-10);
        }catch (IllegalArgumentException e) {
            System.out.println("Товар со скидкой не создан: " + e);
        }

        testSearchRelevant();

        testRemoveEngine();
    }

    private static void testSearchEngine(){
        System.out.println("Main.testSearchEngine - TreeMap");
        //Создайте один объект типа SearchEngine и добавьте в него все товары
        SearchEngine searchEngine = new SearchEngine();
        // добавление объектов
        searchEngine.add(new Article("Погодные условия","12/05/ Погода дождливая...."));
        searchEngine.add(new SimpleProduct("Погодный зонт",100));
        searchEngine.add(new SimpleProduct("Фонарь",50));
        searchEngine.add(new Article("Солнечная погода", "Сегодня солнечно..."));
        searchEngine.add(new Article("Дождь", "Идёт сильный дождь..."));
        searchEngine.add(new Article("А", "Тест с одной буквой"));
        searchEngine.add(new Article("Б", "Тест с одной буквой"));

        //создание поискового массива (добавление объектов)
        String query = "Погод";
        System.out.println("Результат поиска по поисковому значению = " + query);
        // Выполняем поиск
        // List<Searchable> found = searchEngine.search(query); // старый вариант под лист
        // Map<String, Searchable> found = searchEngine.search(query); // новый вариант под Map
        TreeSet<Searchable> found = searchEngine.search(query); // новый вариант под TreeSet

        // Выводим результаты для HashSet
        if (found.isEmpty()) {
            System.out.println("Ничего не найдено!");
        } else {
            System.out.println("Найдено " + found.size() + " результатов:");
            for (Searchable i : found) {
                System.out.println("  - " + i.getStringRepresentation());
            }
        }
    }

    private static void testSearchRelevant() {
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.add(new Article("Погодные условия","12/05/ Погода дождливая...."));
        searchEngine.add(new Article("Рисование","Фломастер рисует среднюю линию"));
        searchEngine.add(new Article("Рисование","Шариковая ручка рисует тонкую линию"));
        searchEngine.add(new Article("Художество","Кисть рисует толстую линию"));

        String query = "летает";
        System.out.println("\nРелевантный поиск '" + query + "':");

        try {
            Searchable[] relevant = searchEngine.searchRelevant(query);
            System.out.println("Найдено релевантных результатов: " + relevant.length);
            for (Searchable item : relevant) {
                if (item != null) {
                    System.out.println("  - " + item.getName() + ": " + item.getSearchTerm());
                }
            }
        } catch (BestResultNotFound e) {
            System.out.println("Результат: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }


// ==============================
// МЕТОД ДЛЯ РАБОТЫ С  List
// ==============================

    private static void testRemoveEngine(){
        System.out.println("Main.testRemoveEngine");
        //Создайте один объект типа SearchEngine и добавьте в него все товары
        SearchEngine searchEngine = new SearchEngine();
        // добавление объектов
        searchEngine.add(new Article("Погодные условия","12/05/ Погода дождливая...."));
        searchEngine.add(new SimpleProduct("Погодный зонт",100));
        searchEngine.add(new SimpleProduct("Фонарь",50));

        //создание поискового массива (добавление объектов)
        String query = "Фонарь";
        System.out.println("Удаляем товары по запросу: " + query);
        // Выполняем удаление
        HashSet<Searchable> removed = searchEngine.removeProductBasket(query);

        // выводим результат
        if (removed.isEmpty()) {
            System.out.println("Товары не найдены для удаления!");
        } else {
            System.out.println("Удалено товаров: " + removed.size());
            for (Searchable item : removed) {
                System.out.println("  - " + item.getStringRepresentation());
            }
        }

        // Показываем корзину после удаления
        System.out.println("\nКорзина после удаления:");
        HashSet<Searchable> remaining = searchEngine.getSearchables();
        if (remaining.isEmpty()) {
            System.out.println("Корзина пуста!");
        } else {
            for (Searchable item : remaining) {
                System.out.println("  - " + item.getStringRepresentation());
            }
        }
    }
}

