package application.controllers;


import application.modelfx.CategoryFx;
import application.modelfx.CategoryModel;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.sql.SQLException;

public class CategoryController {

    @FXML
    private Button categoryDeleteButton;

    @FXML
    private Button categoryEditButton;

    @FXML
    private TreeView<String> categoryTreeView;

    @FXML
    private Button categoryAddButton;

    @FXML
    private TextField categoryTextField;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    private CategoryModel categoryModel;

    public void initialize() {
        this.categoryModel = new CategoryModel();
        try {
            this.categoryModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
        this.categoryTreeView.setRoot(this.categoryModel.getRoot());
        initBindings();
    }

    private void initBindings() {
        this.categoryAddButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        this.categoryDeleteButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());
        this.categoryEditButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());
    }

    public void addCategoryOnAction() {
        try {
            categoryModel.saveCategoryInDataBase(categoryTextField.getText());
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
        categoryTextField.clear();
    }

    public void deleteCategoryOnAction() {
        try {
            this.categoryModel.deleteCategoryById();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
    }

    public void onActionComboBo() {
        this.categoryModel.setCategory(this.categoryComboBox.getSelectionModel().getSelectedItem());
    }

    public void editCategoryOnAction() {
        String newCategoryName = DialogsUtils.dialogEdit(this.categoryModel.getCategory().getName());
        if (newCategoryName != null) {
            this.categoryModel.getCategory().setName(newCategoryName);
            try {
                this.categoryModel.updateCategoryInDataBase();
            } catch (ApplicationException e) {
                DialogsUtils.dialogError(e.getMessage());
            }
        }

    }
}
