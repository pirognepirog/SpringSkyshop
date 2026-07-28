package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public final class SearchService extends StorageService {

        private final StorageService storageService; // зависимость

    // Конструктор для внедрения зависимости
    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    // Метод поиска
    public Collection<SearchResult> search(String query) {
        // проверка на null
        if (query == null || query.isBlank()) {
            return Collections.emptyList(); // возврат, пустой лист
        }

        String lowerQuery = query.toLowerCase();

        // Объединяем продукты и статьи в один поток
        return storageService.getAllProduct().stream()
                .filter(item -> item.getSearchTerm() != null &&
                        item.getSearchTerm().toLowerCase().contains(lowerQuery))
                .map(SearchResult::fromSearchable)   // преобразуем каждый Searchable в SearchResult
                .collect(Collectors.toList());       // собираем в коллекцию
    }



}
