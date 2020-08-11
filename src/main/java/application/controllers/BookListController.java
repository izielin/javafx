/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.controllers;

import application.modelfx.AuthorFx;
import application.modelfx.BookFx;
import application.modelfx.BookListModel;
import application.modelfx.CategoryFx;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class BookListController {

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private ComboBox<AuthorFx> authorComboBox;

    @FXML
    private TableView<BookFx> booksTableView;

    @FXML
    private TableColumn<BookFx, String> titleColumn;

    @FXML
    private TableColumn<BookFx, AuthorFx> authorColumn;

    @FXML
    private TableColumn<BookFx, CategoryFx> categoryColumn;

    @FXML
    private TableColumn<BookFx, String> descriptionColumn;

    @FXML
    private TableColumn<BookFx, Number> ratingColumn;

    @FXML
    private TableColumn<BookFx, String> isbnColumn;

    @FXML
    private TableColumn<BookFx, LocalDate> releaseColumn;

    private BookListModel bookListModel;

    @FXML
    void initialize() {
        this.bookListModel = new BookListModel();
        try {
            this.bookListModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }

        this.authorComboBox.setItems(this.bookListModel.getAuthorFxObservableList());
        this.categoryComboBox.setItems(this.bookListModel.getCategoryFxObservableList());

        this.bookListModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.bookListModel.authorFxObjectPropertyProperty().bind(this.authorComboBox.valueProperty());

        this.booksTableView.setItems(this.bookListModel.getBookFxObservableList());
        setCellsValues();
    }

    private void setCellsValues() {
        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
        this.isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        this.releaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorFxProperty());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty());
    }

    public void filerOnAction() {
        this.bookListModel.filterBookList();
    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.getSelectionModel().clearSelection();
    }

    public void clearAuthorComboBox() {
        this.authorComboBox.getSelectionModel().clearSelection();

    }
}
