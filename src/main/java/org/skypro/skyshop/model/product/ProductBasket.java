package org.skypro.skyshop.model.product;
import org.skypro.skyshop.model.product.SimpleProduct;
import java.util.*;


public class ProductBasket {

    private Map<String, List<Product>> products;
   // private int count;
   // private int totalPrice;
    private static final String DEFAULT_CATEGORY = "Все товары";


    public ProductBasket() { // int size - убран, так как List не имеет размера (динамический набор)
        this.products = new HashMap<>();
       // this.count = 0;
       //this.totalPrice = 0;
    }
    // метод для добавления по имени и цене
    public void addProduсtInBasket(String name, int prise) {
        Product product = new SimpleProduct(name,prise);
        addProduсtInBasket(product);
    }

    // новый метод для перегрузки метода addProduсtInBasket (добавление по объекту)
    public void addProduсtInBasket(Product product) {
        if (products == null) {
            System.out.println("Корзина не создана, отсутствует продукт!");
            return;
        }
        // добавление в категорию DEFAULT_CATEGORY, так как работаем с Map, нужен ключ
        products.computeIfAbsent(DEFAULT_CATEGORY, k -> new ArrayList<>()).add(product);
    }

    // Метод подсчёта специальных товаров (FixPriceProduct и DiscountedProduct)
    public int getSpecialProductCount() {
      return (int) products.values().stream()
              .flatMap(Collection::stream)
              .filter(Objects::nonNull)
              .filter(Product::isSpecial)
              .count();
    }

    public int getTotalPrice () {
        return products.values().stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .mapToInt(Product::getPriceProduct)
                .sum();
    }

    public void printBacket() {
        if (products.isEmpty()) {
            System.out.println("В корзине пусто!");
            return;
        }
        // StreamAPI для печати
        products.values().stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .forEach(System.out::println);

        System.out.println("Итого: " + getTotalPrice()  + " руб.");
    }

    // проверка продукта по имени
    public boolean verificationBacket(String nameProduct) {
            boolean found = products.values().stream()
                    // .values() - возвращает все значения из Map<String, List<Product>>
                    // .stream() - преобразует .values() в поток  (stream)
                            .flatMap(Collection::stream)//  "разворачивает" вложенные коллекции.
                                                        // Collection::stream — ссылка на метод, который превращает
                                                        // каждый List<Product> в Stream<Product>.
                            .filter(Objects::nonNull)
                            .anyMatch(product -> product.getNameProduct().equals(nameProduct));
                            // .anyMatch - проверяет, соответствует ли хотя бы один элемент потока условию.
                            //product -> product.getNameProduct().equals(nameProduct) — лямбда-выражение (предикат):
                            //Для каждого товара (product) вызывает getNameProduct() и сравнивает с искомым именем (nameProduct).
            if (found) {
                System.out.println("В корзине найдено наименование продукта: " + nameProduct);
            } else {
                System.out.println("В корзине не найдено наименование продукта: " + nameProduct);
            }
    return found;
    }

    public void clearBacket() {
        products.clear();
        //totalPrice = 0;
        System.out.println("Очистка корзины завершена!");
    }


}