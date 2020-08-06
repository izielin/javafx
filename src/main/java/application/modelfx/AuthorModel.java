/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.modelfx;

import application.database.dao.AuthorDao;
import application.database.dbutils.DbManager;
import application.database.models.Author;
import application.utils.converters.AuthorConverter;
import application.utils.exceptions.ApplicationException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AuthorModel {
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());

    public void saveAuthor() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao(DbManager.getConnectionSource());
        Author author = AuthorConverter.convertAuthorFxTOAuthor(this.getAuthorFxObjectProperty());
        authorDao.createOrUpdate(author);
        DbManager.closeConnectionSource();
    }


    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }
}
