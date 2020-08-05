package application.controllers;


import application.modelfx.CategoryFx;
import application.modelfx.CategoryModel;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CategoryController {

    @FXML
    public Button categoryDeleteButton;

    @FXML
    public Button categoryEditButton;

    @FXML
    private Button categoryAddButton;

    @FXML
    private TextField categoryTextField;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    private CategoryModel categoryModel;

    @FXML
    public void initialize() {
        this.categoryModel = new CategoryModel();
        try {
            this.categoryModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
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
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
    }

    public void onActionComboBo() {
        this.categoryModel.setCategory(this.categoryComboBox.getSelectionModel().getSelectedItem());
    }

    public void editCategoryOnAction(ActionEvent actionEvent) {
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
