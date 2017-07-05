/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("SQL Project");
        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
