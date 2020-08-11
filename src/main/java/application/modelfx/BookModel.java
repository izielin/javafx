/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.modelfx;

import application.database.dao.AuthorDao;
import application.database.dao.BookDao;
import application.database.dao.CategoryDao;
import application.database.models.Author;
import application.database.models.Book;
import application.database.models.Category;
import application.utils.converters.BookConverter;
import application.utils.exceptions.ApplicationException;
import application.utils.initializers.Initializers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookModel {

    private static ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private static ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());

    public void init() throws ApplicationException {
        Initializers.initAuthorList(authorFxObservableList);
        Initializers.initCategoryList(categoryFxObservableList);
    }


    public void saveBook() throws ApplicationException {
        Book book = BookConverter.convertToBook(this.getBookFxObjectProperty());
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getBookFxObjectProperty().getCategoryFx().getId());

        AuthorDao authorDao = new AuthorDao();
        Author author = authorDao.findById(Author.class, this.getBookFxObjectProperty().getAuthorFx().getId());

        book.setCategory(category);
        book.setAuthor(author);

        BookDao bookDao = new BookDao();
        bookDao.createOrUpdate(book);
    }

    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

}
