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
import application.utils.converters.AuthorConverter;
import application.utils.converters.BookConverter;
import application.utils.converters.CategoryConverter;
import application.utils.exceptions.ApplicationException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BookModel {

    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());

    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {
        initAuthorList();
        initCategoryList();
    }

    private void initAuthorList() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorFxObservableList.clear();
        authorList.forEach(c -> {
            AuthorFx authorFx = AuthorConverter.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });
    }

    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFx categoryFx = CategoryConverter.convertToCategoryFx(c);
            categoryFxObservableList.add(categoryFx);
        });
    }

    public void saveBook() throws ApplicationException {
        Book book = BookConverter.convertToBook(this.getBookFxObjectProperty());
        CategoryDao categoryDao= new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getBookFxObjectProperty().getCategoryFxObjectProperty().getId());

        AuthorDao authorDao= new AuthorDao();
        Author author = authorDao.findById(Author.class, this.getBookFxObjectProperty().getAuthorFxObjectProperty().getId());

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
