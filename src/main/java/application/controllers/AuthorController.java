/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.controllers;

import application.modelfx.AuthorFx;
import application.modelfx.AuthorModel;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;

public class AuthorController {

    @FXML
    private TextField authorNameTextField;

    @FXML
    private TextField authorSurnameTextField;

    @FXML
    private Button addButton;

    @FXML
    private TableView<AuthorFx> authorTableView;

    @FXML
    private TableColumn<AuthorFx, String> nameColumn;

    @FXML
    private TableColumn<AuthorFx, String> surnameColumn;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem showMenuItem;


    private AuthorModel authorModel;

    public void initialize() {
        this.authorModel = new AuthorModel();
        try {
            this.authorModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
        bindings();
        bindingsTableView();
    }

    private void bindingsTableView() {
        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.authorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.authorModel.setAuthorFxObjectPropertyEdit(newValue));
    }

    private void bindings() {
        this.authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.authorNameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.authorSurnameTextField.textProperty());
        this.addButton.disableProperty().bind(this.authorNameTextField.textProperty().isEmpty().or(this.authorSurnameTextField.textProperty().isEmpty()));
        this.deleteMenuItem.disableProperty().bind(this.authorTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void addAuthorOnAction() {
        try {
            this.authorModel.saveAuthor();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
        this.authorNameTextField.clear();
        this.authorSurnameTextField.clear();
    }

    public void onEditCommitName(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEdit().setName(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEdit().setSurname(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    private void updateInDatabase() {
        try {
            this.authorModel.saveAuthorEdit();
        } catch (ApplicationException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
    }

    public void deleteAuthorOnAction() {
        try {
            this.authorModel.deleteAuthor();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.dialogError(e.getMessage());
        }
    }

    public void showBooksOnAction(ActionEvent actionEvent) {
    }
}
