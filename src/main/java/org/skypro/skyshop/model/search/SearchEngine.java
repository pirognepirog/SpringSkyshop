package org.skypro.skyshop.model.search;

import org.skypro.skyshop.model.article.BestResultNotFound;

import java.util.*;
import java.util.stream.Collectors;

public class SearchEngine {
    // константа для ограничения поиска
    // private static final int MAX_SIZE = 5;
    // массив строк для поиска
    // private List<Searchable> searchables;  // работа с листами

    private HashSet<Searchable> searchables; // работа с Set

    //котструктор размерности массива
    public SearchEngine() {
        // this.searchables = new LinkedList<>(); //[MAX_SIZE]; - не нужен, так как нет размерности, РАБОТА С ЛИСТАМИ
        this.searchables = new HashSet<>(); // РАБОТА С Set
    }

    // новый метод для работы поискового движка для поиска предметов
    public void add(Searchable item){
        // проверка на null
        if (item == null) {
            System.out.println("Нет товара для добавления в корзину!");
            return;
        }
        // проверка на то, что в корзине элемент уже существует
        if (searchables.contains(item)) {
            System.out.println(item + " - этот товар уже добавлен в корзину!");
            return;
        }
        searchables.add(item); // ложим товар в корзину
    }

    //===================================================
    // РЕАЛИЗАЦИЯ ДЛЯ SET    РЕАЛИЗАЦИЯ ДЛЯ SET
    // ==================================================

    public TreeSet<Searchable> search(String query) {
        String lowerQuery = query.toLowerCase();  // преобразованная поисковая строка к нижнему регистру
                                                  // для того, чтобы не зависеть от регистра
        return searchables.stream() // открытие потока для searchables
                .filter(Objects::nonNull) // убираю null (для исключения исключений ...)
                .filter(i -> { // принимаю поисковый объект из TreeSet<Searchable> и присваиваю его для term
                    String term = i.getSearchTerm(); // в проекте "зонт", к примеру
                    return term != null && term.toLowerCase().contains(lowerQuery); // сравниваю объекты в
                                                                                    // нижнем регистре
                    // в текущем проекте = зонт.toLowerCase().contains(зонт)
                    // term != null  - чтобы не провалиться в ошибку, для страховки
                    // конечно можно обработать иначе через исключение, например через Message и не работать
                    // но у нас бекенд, это для фронтенда задача
                    // в результате работа сравнима оператору like для "*" term "*" в SQL (VBA)
                })
                .collect(Collectors.toCollection(()-> new TreeSet<>(new SearchableComparator())));
                // завершаю поток, терминальная операция со сборкой в новый TreeSet с SearchableComparator
    }


    // метод - поиск релевантного значения
    // кинул исправленный код для проверкии ИИ, в результате было обнаружено, что код по массиву проходит 3 раза
    // что не является оптимальным, тут использовал код, предложенный ИИ

    public Searchable[] searchRelevant(String query) throws BestResultNotFound {
        // Проверка на null или пустую строку
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Введено некорректное поисковое значение!");
        }

        // Используем Map для хранения результатов и их релевантности
        Map<Searchable, Integer> relevanceMap = new HashMap<>();
        int maxCount = 0;

        for (Searchable item : searchables) {
            if (item != null) {
                String searchTerm = item.getSearchTerm();
                if (searchTerm != null) {
                    int count = countInStr(searchTerm, query);
                    if (count > 0) {
                        relevanceMap.put(item, count);
                        if (count > maxCount) {
                            maxCount = count;
                        }
                    }
                }
            }
        }

        // Если ничего не найдено
        if (relevanceMap.isEmpty() || maxCount == 0) {
            throw new BestResultNotFound(query);
        }

        // Собираем результаты с максимальной релевантностью
        List<Searchable> result = new ArrayList<>();
        for (Map.Entry<Searchable, Integer> entry : relevanceMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                result.add(entry.getKey());
            }
        }

        return result.toArray(new Searchable[0]);
    }

    // вспомогительный метод для подсчета вхождения строки в подстроку
    private int countInStr(String text, String subString) {

        if (text == null || subString == null || subString.isEmpty()) {
            return 0;
        }

        int count = 0;
        int index = 0;
        int subStrInd = text.indexOf(subString, index);

        while (subStrInd != -1) {
            count++;
            index = subStrInd + subString.length();
            subStrInd = text.indexOf(subString, index);
        }
        return count;
    }

    //===================================================
    // РЕАЛИЗАЦИЯ ДЛЯ Set    РЕАЛИЗАЦИЯ ДЛЯ Set
    // ==================================================

    // метод для удаления из корзины
    public HashSet<Searchable> removeProductBasket(String query) {
        // обьявляю список результатов
        HashSet<Searchable> removedProducts  = new HashSet<>();

        // проверяем, есть ли в корзине товары, до того как выполнять поиск
        if (searchables.isEmpty()) {
            System.out.println("Корзина пуста!");
            return removedProducts; // возвращаем пустой список
        }
        // использование итератора для удаления
        Iterator<Searchable> iterator = searchables.iterator();
        while (iterator.hasNext()) {
            Searchable i = iterator.next();
            if (i != null) {
                String searchTerm = i.getSearchTerm();
                if (searchTerm != null && searchTerm.contains(query)) {
                    removedProducts.add(i); // добавление в список удаленных
                    iterator.remove(); // удаление из корзины через итератор
                }
            }
        }
        // проверка, пуста ли стала корзина
        if (searchables.isEmpty()) {
            System.out.println("В корзине больше нечего нет!");
        }

        return removedProducts;
    }

    public boolean isEmpty() {
        return searchables.isEmpty();
    }

    //===================================================
    // РЕАЛИЗАЦИЯ ДЛЯ SET    РЕАЛИЗАЦИЯ ДЛЯ SET
    // =================================================
    public HashSet<Searchable> getSearchables() {
         return searchables;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchEngine that = (SearchEngine) o;
        return Objects.equals(searchables, that.searchables);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(searchables);
    }
}
