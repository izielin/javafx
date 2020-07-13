package application.controllers;

import application.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TopMenuButtonsController  topMenuButtonsController;

    @FXML
    private void initialize(){
        topMenuButtonsController.setMainController(this);
    }

    public void setCenter (String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(parent);
    }

    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    public void setModena() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    public void setAlwaysOnTop(ActionEvent actionEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        boolean currentState = ((CheckMenuItem)actionEvent.getSource()).selectedProperty().get();
        stage.setAlwaysOnTop(currentState);
    }

    public void showAbout() {
    }
}
