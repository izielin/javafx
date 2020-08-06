package application.utils.converters;

import application.database.models.Category;
import application.modelfx.CategoryFx;

public class CategoryConverter {
    public static CategoryFx convertToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }


}
