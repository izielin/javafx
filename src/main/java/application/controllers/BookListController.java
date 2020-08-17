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
import application.utils.FxmlUtils;
import application.utils.exceptions.ApplicationException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BookListController {

    public static final String DELETE_ICON = "/icons/times.png";
    public static final String EDIT_ICON = "/icons/edit.png";


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

    @FXML
    private TableColumn<BookFx, BookFx> deleteColumn;

    @FXML
    private TableColumn<BookFx, BookFx> editColumn;


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
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.deleteColumn.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            final Button button = createButton(DELETE_ICON);

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        try {
                            bookListModel.deleteBook(item);
                        } catch (ApplicationException e) {
                            DialogsUtils.dialogError(e.getMessage());
                        }
                    });
                } else {
                    setGraphic(null);
                }
            }

        });

        this.editColumn.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            final Button button = createButton(EDIT_ICON);

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddBook.fxml");
                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        BookController controller = loader.getController();
                        controller.getBookModel().setBookFxObjectProperty(item);
                        controller.bindings();
                        controller.addButton.setVisible(false);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality((Modality.APPLICATION_MODAL));
                        stage.showAndWait();
                    });
                } else {
                    setGraphic(null);
                }
            }

        });
    }

    private Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
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
