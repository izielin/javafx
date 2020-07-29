package application;

import application.utils.FxmlUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    public static final String FXML_BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("en"));
        Pane borderPane = FxmlUtils.fxmlLoader(FXML_BORDER_PANE_MAIN_FXML);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("applicationTitle"));
        primaryStage.show();
    }
}
