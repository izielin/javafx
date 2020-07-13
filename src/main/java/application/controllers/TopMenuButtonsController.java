package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class TopMenuButtonsController {
    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String BOOK_LIST_FXML = "/fxml/BookList.fxml";
    public static final String STATISTIC_FXML = "/fxml/Statistic.fxml";
    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";

    public ToggleGroup toggleButtons;
    private MainController mainController;

    @FXML
    public void openLibrary(ActionEvent actionEvent) {
        mainController.setCenter(LIBRARY_FXML);
    }

    @FXML
    public void openList(ActionEvent actionEvent) {
        mainController.setCenter(BOOK_LIST_FXML);
    }

    @FXML
    public void openStatistics(ActionEvent actionEvent) {
        mainController.setCenter(STATISTIC_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addBook(ActionEvent actionEvent) {
        if (toggleButtons.getSelectedToggle() != null)
            toggleButtons.getSelectedToggle().setSelected(false);
        mainController.setCenter(ADD_BOOK_FXML);
    }
}
