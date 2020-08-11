/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.controllers;

import application.modelfx.AuthorFx;
import application.modelfx.BookModel;
import application.modelfx.CategoryFx;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookController {
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private ComboBox<AuthorFx> authorComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Slider ratingSlider;

    @FXML
    private TextField isbnTextField;

    @FXML
    private DatePicker releaseDatePicker;

    @FXML
    private TextField titleTextField;

    private BookModel bookModel;

    @FXML
    void initialize() {
        this.bookModel = new BookModel();

        try {
            this.bookModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }

        bindings();
    }

    private void bindings() {
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());
        this.bookModel.getBookFxObjectProperty().categoryFxProperty().bind(this.categoryComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().authorFxProperty().bind(this.authorComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().titleProperty().bind(this.titleTextField.textProperty());
        this.bookModel.getBookFxObjectProperty().descriptionProperty().bind(this.descriptionTextArea.textProperty());
        this.bookModel.getBookFxObjectProperty().ratingProperty().bind(this.ratingSlider.valueProperty());
        this.bookModel.getBookFxObjectProperty().isbnProperty().bind(this.isbnTextField.textProperty());
        this.bookModel.getBookFxObjectProperty().releaseDateProperty().bind(this.releaseDatePicker.valueProperty());
    }

    public void addBookOnAction() {
        try {
            this.bookModel.saveBook();
            clearFields();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
    }

    private void clearFields() {
        this.authorComboBox.getSelectionModel().clearSelection();
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.titleTextField.clear();
        this.descriptionTextArea.clear();
        this.ratingSlider.setValue(1);
        this.isbnTextField.clear();
        this.releaseDatePicker.getEditor().clear();
    }

}
