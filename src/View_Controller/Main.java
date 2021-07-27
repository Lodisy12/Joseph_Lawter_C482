package View_Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
Java Docs are located at InventoryManagement\javadoc
 */

/**
 * This is the main class where the main method lies.
 * @author Joseph Lawter
 * FUTURE ENHANCEMENT: I believe the ability to filter the lists on multiple
 * criteria would be useful. For instance, being able to filter based on price
 * and stock would be helpful in some situations.
 * I would add another section to the parts table that includes the Machine ID/
 * Company Name and indicate whether it was inHouse or Outsourced on the table.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        primaryStage.setTitle("Main Scene");
        primaryStage.setScene(new Scene(root, 1175, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
