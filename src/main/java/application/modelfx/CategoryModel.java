package application.modelfx;

import application.database.dao.CategoryDao;
import application.database.dbutils.DbManager;
import application.database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryModel {

    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>();

    public void init() {
        CategoryDao categoryDao = new CategoryDao(DbManager.getConnectionSource());
        List<Category> categories = categoryDao.queryForAll(Category.class);
        this.categoryList.clear();
        categories.forEach(c -> {
            CategoryFx categoryFx = new CategoryFx();
            categoryFx.setId(c.getId());
            categoryFx.setName(c.getName());
            this.categoryList.add(categoryFx);
        });
        DbManager.closeConnectionSource();
    }

    public void deleteCategoryById() {
        CategoryDao categoryDao = new CategoryDao(DbManager.getConnectionSource());
        categoryDao.deleteById(Category.class, category.getValue().getId());
        init();
        DbManager.closeConnectionSource();

    }

    public void saveCategoryInDataBase(String name) {
        CategoryDao categoryDao = new CategoryDao(DbManager.getConnectionSource());
        Category category = new Category();
        category.setName(name);
        categoryDao.createOrUpdate(category);
        DbManager.closeConnectionSource();
        init();
    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }
}
