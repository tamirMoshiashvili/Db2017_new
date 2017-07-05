/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import sample.query.DdlQueryExecutor;
import sample.query.DmlQueryExecutor;
import sample.query.QueryExecutor;
import sample.query.ScriptQueryExecutor;

import java.io.IOException;

/**
 * Controller for the main window.
 */
public class MainController {

    // buttons
    @FXML
    private Button infoBtn;
    @FXML
    private Button dmlBtn;
    @FXML
    private Button ddlBtn;
    @FXML
    private Button scriptBtn;
    @FXML
    private Button simpleBtn;

    /**
     * create new window (stage) according to the given parameters.
     *
     * @param file   string representing the name of fxml file.
     * @param title  string representing the title of the new stage.
     * @param width  width of new stage.
     * @param height height of new stage.
     * @return stage.
     */
    private Stage makeNewWindow(String file, String title, int width, int height) {
        // load fxml file
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // create stage
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(parent, width, height);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    /**
     * create regular window (stage) with fixed sized.
     *
     * @param file          fxml file path.
     * @param title         stage title.
     * @param queryExecutor query object to perform queries.
     */
    private void makeRegularWindow(String file, String title, QueryExecutor queryExecutor) {
        // set query
        DbManager.queryExecutor = queryExecutor;
        // create stage
        Stage stage = makeNewWindow(file, title, 300, 400);
        if (stage != null) {
            QueryController.currentStage = stage;
        }
    }

    /**
     * show information window.
     *
     * @param event event.
     */
    public void infoClick(MouseEvent event) {
        makeNewWindow("Information.fxml", "Information", 300, 400);
    }

    /**
     * show DML window.
     *
     * @param mouseEvent event.
     */
    public void dmlClick(MouseEvent mouseEvent) {
        makeRegularWindow("queryGui.fxml", "DML", new DmlQueryExecutor());
    }

    /**
     * show DDL window.
     *
     * @param mouseEvent event.
     */
    public void ddlClick(MouseEvent mouseEvent) {
        makeRegularWindow("queryGui.fxml", "DDL", new DdlQueryExecutor());
    }

    /**
     * show Script window.
     *
     * @param mouseEvent event.
     */
    public void scriptClick(MouseEvent mouseEvent) {
        makeRegularWindow("queryGui.fxml", "Script", new ScriptQueryExecutor());
    }

    /**
     * show Simple query window.
     *
     * @param mouseEvent event.
     */
    public void simpleClick(MouseEvent mouseEvent) {
        Stage stage = makeNewWindow("simpleQueryWindow.fxml", "Simple Query", 600, 500);
        if (stage != null) {
            SimpleQueryController.currentStage = stage;
        }
    }
}
