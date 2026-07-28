package org.skypro.skyshop.model.product;

public class DiscountedProduct extends Product {
   private int basePrice; //базовая цена
    private int discountPercent; //скидка в целых процентах

    public DiscountedProduct(String nameProduct, int basePrice, int discountPercent) {
        super(nameProduct);

        if (basePrice < 1) {
            throw new IllegalArgumentException("Базовая цена продукта быть больше 0! Для " +
                    nameProduct + " указана базовая цена = " + basePrice);
        }
        if (!(discountPercent >= 0 && discountPercent <= 100)) {
            throw new IllegalArgumentException(nameProduct + ": скидка должна лежать в диапазоне от 0 до 100%" +
                    ", задано значение = " + discountPercent + "; это не верно!");
        }

        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
    }

    @Override
    public boolean isSpecial() {
        return true;  // специальный товар
    }

    @Override
    public int getPriceProduct() {
        return basePrice * (100 - discountPercent) / 100; // цена со скидкой
    }

    @Override
    public String toString() {
        return "продукт: " + getNameProduct() + ", стоимость " + getPriceProduct() + " руб. (скидка " + discountPercent + "%)";
    }


    @Override
    public String getSearchTerm() {
        return getNameProduct();
    }

    @Override
    public String getContentType() {
        return getNameProduct() + " цена " + getPriceProduct() + " руб.";
    }

    @Override
    public String getName() {
        return getNameProduct();
    }
}
