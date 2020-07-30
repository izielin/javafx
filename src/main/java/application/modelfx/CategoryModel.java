package application.modelfx;

import application.database.dao.CategoryDao;
import application.database.dbutils.DbManager;
import application.database.models.Category;

public class CategoryModel {
    public void saveCategoryInDataBase (String name){
        CategoryDao categoryDao = new CategoryDao(DbManager.getConnectionSource());
        Category category = new Category();
        category.setName(name);
        categoryDao.createOrUpdate(category);
        DbManager.closeConnectionSource();
    }
}
