/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.modelfx;

import application.database.dao.BookDao;
import application.database.models.Book;
import application.utils.converters.BookConverter;
import application.utils.exceptions.ApplicationException;
import application.utils.initializers.Initializers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookListModel {
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();

    private final ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();

    private final List<BookFx> bookFxList = new ArrayList<>();

    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.queryForAll(Book.class);
        bookFxList.clear();
        books.forEach(book -> this.bookFxList.add(BookConverter.convertToBookFx(book)));

        this.bookFxObservableList.setAll(bookFxList);

        Initializers.initAuthorList(authorFxObservableList);
        Initializers.initCategoryList(categoryFxObservableList);
    }

    public void deleteBook(BookFx item) throws ApplicationException {
        BookDao bookDao = new BookDao();
        bookDao.deleteById(Book.class, item.getId());
        init();
    }

    public void filterBookList() {
        if (getAuthorFxObjectProperty() != null && getCategoryFxObjectProperty() != null) {
            filterPredicate(predicateAuthor().and(predicateCategory()));
        } else if (getCategoryFxObjectProperty() != null) {
            filterPredicate(predicateCategory());
        } else if (getAuthorFxObjectProperty() != null) {
            filterPredicate(predicateAuthor());
        } else {
           this.bookFxObservableList.setAll(this.bookFxList);
        }
    }

    private Predicate<BookFx> predicateCategory() {
        return bookFx -> bookFx.getCategoryFx().getId() == getCategoryFxObjectProperty().getId();
    }

    private Predicate<BookFx> predicateAuthor() {
        return bookFx -> bookFx.getAuthorFx().getId() == getAuthorFxObjectProperty().getId();
    }

    private void filterPredicate(Predicate<BookFx> predicate) {
        List<BookFx> filteredList = bookFxList.stream().filter(predicate).collect(Collectors.toList());
        this.bookFxObservableList.setAll(filteredList);
    }

    public ObservableList<BookFx> getBookFxObservableList() {
        return bookFxObservableList;
    }

    public void setBookFxObservableList(ObservableList<BookFx> bookFxObservableList) {
        this.bookFxObservableList = bookFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

}
