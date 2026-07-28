package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;
import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {

    private final String nameProduct;
    private final UUID id;

    //создаю конструктор
    public Product(String nameProduct) {

        if (nameProduct == null || nameProduct.isBlank()) {
            throw new IllegalArgumentException("Название продукта не корректно!");
        }
        this.nameProduct = nameProduct;
        this.id = UUID.randomUUID();
    }

    // инициализация геттеров для чтения данных класса

    public String getNameProduct() {
        return nameProduct;
    }

    public abstract boolean isSpecial();

    public abstract int getPriceProduct();// {return priceProduct;

    @Override
    public String toString() {
        return nameProduct + ": " + getPriceProduct() + " руб.";// + priceProduct;
    }

    // методы интерфейса Searchable

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonIgnore // чтобы не выводилась строка getSearchTerm
    public String getSearchTerm() {
        return getNameProduct();
    }

    @Override
    public String getContentType() {
        return nameProduct + " цена " + getPriceProduct() + " руб.";
    }

    @Override
    public String getName() {
        return nameProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(nameProduct, product.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameProduct);
    }
}



