package org.skypro.skyshop.model.search;

import java.util.Comparator;

public class SearchableComparator implements Comparator<Searchable> {

    @Override
    public int compare (Searchable o1, Searchable o2) {
        // пролучаем имена объектов
        String name1 = o1.getName();
        String name2 = o2.getName();

        // сравнение по длине имени
        int lenghtName = Integer.compare(name2.length(), name1.length());

        if (lenghtName != 0) { // Если длины разные, возвращаем результат сравнения длин
            return lenghtName;
        }

        // Если длины одинаковые, сравниваем в натуральном порядке
        return name1.compareToIgnoreCase(name2);

    }
}
