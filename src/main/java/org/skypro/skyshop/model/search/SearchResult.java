package org.skypro.skyshop.model.search;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public final class SearchResult {

    // по заданию класс должен быть не изменяемым (immutalbe)
    // правила для такого класса:
        // 1. все поля должны быть private final
        // 2. сам класс должен быть final
        // 3. все поля должны быть инициализированны в конструкторе
        // 4. не должно быть сеттеров - чтобы нельзя было изменить поля
        // 5. Если поле — изменяемый объект (например, List), нужно возвращать его копию или неизменяемую версию

    private final UUID id;
    private final String name;
    private final String contentType;

    public SearchResult(UUID id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    // геттеры
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }
// напишите публичный статический метод fromSearchable внутри класса SearchResult.
    // Метод должен принимать Searchable и возвращать SearchResult с заполненными полями.

    public static SearchResult fromSearchable(Searchable searchable) {
        return new  SearchResult(
                searchable.getId(),
                searchable.getName(),
                searchable.getContentType()
        );
    }
}
