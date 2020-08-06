/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.controllers;

import application.modelfx.AuthorModel;
import application.utils.DialogsUtils;
import application.utils.exceptions.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthorController {

    @FXML
    public TextField authorNameTextField;

    @FXML
    public TextField authorSurnameTextField;

    @FXML
    public Button addButton;

    private AuthorModel authorModel;

    public void initialize() {
        this.authorModel = new AuthorModel();
        this.authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.authorNameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.authorSurnameTextField.textProperty());
        this.addButton.disableProperty().bind(this.authorNameTextField.textProperty().isEmpty().or(this.authorSurnameTextField.textProperty().isEmpty()));
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
}
