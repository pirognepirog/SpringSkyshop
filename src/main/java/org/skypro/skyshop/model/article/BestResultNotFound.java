package org.skypro.skyshop.model.article;

public class BestResultNotFound extends Exception  {
    private final String searchString;

    public BestResultNotFound(String searchString) {
        super("Не найдено ни одного релевантного результата для: " + searchString);
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    @Override
    public String toString() {
        return "BestResultNotFound{searchString='" + searchString + "'}";
    }
}