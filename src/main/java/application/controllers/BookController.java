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
    public Button addButton;

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
        validation();
    }

    private void validation() {
        this.addButton.disableProperty().bind(this.authorComboBox.valueProperty().isNull()
                .or(this.categoryComboBox.valueProperty().isNull())
                .or(this.titleTextField.textProperty().isEmpty())
                .or(this.descriptionTextArea.textProperty().isEmpty())
                .or(this.isbnTextField.textProperty().isEmpty())
                .or(this.releaseDatePicker.valueProperty().isNull())

        );
    }

    public void bindings() {
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());

        this.categoryComboBox.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().categoryFxProperty());
        this.authorComboBox.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().authorFxProperty());
        this.titleTextField.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().titleProperty());
        this.descriptionTextArea.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().descriptionProperty());
        this.ratingSlider.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().ratingProperty());
        this.isbnTextField.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().isbnProperty());
        this.releaseDatePicker.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().releaseDateProperty());
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

    public BookModel getBookModel() {
        return bookModel;
    }
}
