/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for most query types.
 */
public class QueryController {

    /**
     * submit button to execute the query.
     */
    @FXML
    private Button submitBtn;

    /**
     * text area for the user's text, like queries and on.
     */
    @FXML
    private TextArea queryText;

    // used for notes / messages for the user, like error message and on.
    @FXML
    private TextField notesText;
    @FXML
    private Label notesLabel;

    /**
     * current stage for the user.
     */
    public static Stage currentStage = null;

    /**
     * manager for db connection.
     */
    private DbManager dbManager;

    /**
     * constructor.
     */
    public QueryController() {
        // apply connection with db
        this.dbManager = new DbManager();
        this.dbManager.connectToDB();
    }

    /**
     * user clicked on the submit button.
     * execute some query and show its result in the current stage.
     *
     * @param mouseEvent event.
     */
    public void submitClick(MouseEvent mouseEvent) {
        notesText.setText("");
        try {
            // execute query
            String queryStr = queryText.getText();
            if (queryStr.isEmpty()){
                showNotes("Please enter a string in the needed text-area");
                return;
            }
            TableView<List<String>> tableView = this.dbManager.executeQuery(queryStr);
            if (tableView == null) {
                // query has no table-result to show for the user
                showNotes("Success!");
                return;
            }
            // show result-table
            Scene scene = new Scene(tableView, 400, 500);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (SQLException e) {
            String msgErr;
            if (e.getMessage().contains("syntax")) {
                msgErr = "WRONG QUERY STRUCTURE:\n" + e.getMessage();
            } else {
                msgErr = "LOGICAL ERROR:\n" + e.getMessage();
            }
            showNotes(msgErr);
        } catch (IOException e) {
            showNotes(e.getMessage());
        }
    }

    /**
     * show notes in the scene.
     * let the user see the 'notes' section after that.
     *
     * @param msg
     */
    private void showNotes(String msg) {
        notesText.setText(msg);
        notesLabel.setVisible(true);
        notesText.setVisible(true);
    }
}
