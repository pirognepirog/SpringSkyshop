package org.skypro.skyshop.model.product;

public class FixPriceProduct extends Product{

    private static final int FIX_PRICE = 100;

    public FixPriceProduct(String nameProduct) {
        super(nameProduct);
    }

    @Override
    public boolean isSpecial() {
        return true;  // специальный товар
    }

    @Override
    public int getPriceProduct() {
        return FIX_PRICE; // фиксированная цена товара
    }

    @Override
    public String toString() {
        return "Продукт: " + getNameProduct() + " имеет фиксированную цену, равную " + FIX_PRICE + " руб.";
    }

}
