/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.modelfx;

import application.database.dao.AuthorDao;
import application.database.dao.BookDao;
import application.database.dbutils.DbManager;
import application.database.models.Author;
import application.database.models.Book;
import application.utils.converters.AuthorConverter;
import application.utils.exceptions.ApplicationException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class AuthorModel {
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());
    private ObjectProperty<AuthorFx> authorFxObjectPropertyEdit = new SimpleObjectProperty<>(new AuthorFx());
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init () throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author -> {
            AuthorFx authorFx = AuthorConverter.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFx);
        });
    }

    public void saveAuthor() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectProperty());
    }


    public void saveAuthorEdit() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectPropertyEdit());
    }

    private void saveOrUpdate(AuthorFx authorFxObjectPropertyEdit) throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        Author author = AuthorConverter.convertToAuthor(authorFxObjectPropertyEdit);
        authorDao.createOrUpdate(author);
        this.init();
    }

    public void deleteAuthor () throws ApplicationException, SQLException {
        AuthorDao authorDao = new AuthorDao();
        int authorId = this.getAuthorFxObjectPropertyEdit().getId();
        authorDao.deleteById(Author.class, authorId);
        BookDao bookDao = new BookDao();
        bookDao.deleteByColumnName(Book.AUTHOR_ID, authorId);
        this.init();
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

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public AuthorFx getAuthorFxObjectPropertyEdit() {
        return authorFxObjectPropertyEdit.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyEditProperty() {
        return authorFxObjectPropertyEdit;
    }

    public void setAuthorFxObjectPropertyEdit(AuthorFx authorFxObjectPropertyEdit) {
        this.authorFxObjectPropertyEdit.set(authorFxObjectPropertyEdit);
    }
}
