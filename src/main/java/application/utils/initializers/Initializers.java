/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.utils.initializers;

import application.database.dao.AuthorDao;
import application.database.dao.CategoryDao;
import application.database.models.Author;
import application.database.models.Category;
import application.modelfx.AuthorFx;
import application.modelfx.CategoryFx;
import application.utils.converters.AuthorConverter;
import application.utils.converters.CategoryConverter;
import application.utils.exceptions.ApplicationException;
import javafx.collections.ObservableList;

import java.util.List;

public class Initializers {


    public static ObservableList<AuthorFx> initAuthorList(ObservableList<AuthorFx> authorFxObservableList) throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorFxObservableList.clear();
        authorList.forEach(author -> {
            AuthorFx authorFx = AuthorConverter.convertToAuthorFx(author);
            authorFxObservableList.add(authorFx);
        });
        return authorFxObservableList;
    }

    public static ObservableList<CategoryFx> initCategoryList(ObservableList<CategoryFx> categoryFxObservableList) throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(category -> {
            CategoryFx categoryFx = CategoryConverter.convertToCategoryFx(category);
            categoryFxObservableList.add(categoryFx);
        });
        return categoryFxObservableList;
    }
}
