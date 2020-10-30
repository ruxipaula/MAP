package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("RunPrg.fxml"));
        Parent mainWindow = mainLoader.load();

        RunPrgController mainWindowController = mainLoader.getController();

        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(mainWindow, 857, 604));
        primaryStage.show();

        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("SelectPrg.fxml"));
        Parent secondWindow = secondLoader.load();

        SelectPrgController selectWindowController = secondLoader.getController();
        selectWindowController.setMainController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Selecting");
        secondaryStage.setScene(new Scene(secondWindow, 700, 500));
        secondaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
