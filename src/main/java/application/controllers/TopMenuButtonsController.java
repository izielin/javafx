package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TopMenuButtonsController {

    private MainController mainController;

    @FXML
    public void openLibrary(ActionEvent actionEvent) {
        System.out.println("openLibrary");
    }

    @FXML
    public void openList(ActionEvent actionEvent) {
        System.out.println("openList");
    }

    @FXML
    public void openStatistics(ActionEvent actionEvent) {
        System.out.println("openStatistics");
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
