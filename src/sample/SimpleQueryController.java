/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.query.DdlQueryExecutor;
import sample.query.DmlQueryExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * controller for the simple-query gui.
 */
public class SimpleQueryController {

    /**
     * combo box for the tables' namesOfTables.
     */
    @FXML
    private ComboBox<String> tablesNames;

    /**
     * container to hold the columns of the desired table.
     */
    @FXML
    private VBox columnsContainer;

    /**
     * text area for the 'WHERE' section.
     */
    @FXML
    private javafx.scene.control.TextArea whereSection;

    /**
     * field for notes, like when error occurred.
     */
    @FXML
    private TextField notesField;

    /**
     * current stage for the user.
     */
    public static Stage currentStage = null;

    /**
     * object to manage connection with DB.
     */
    private DbManager dbManager;

    /**
     * list to contain all tables' names.
     */
    private ObservableList<String> namesOfTables;

    /**
     * true if the combo box was clicked once, false otherwise.
     */
    private boolean isComboBoxClicked;

    /**
     * list of check boxes, which are the columns of some table.
     */
    private List<CheckBox> checkBoxes;

    /**
     * constructor.
     * get all the tables to show in the combo box.
     */
    public SimpleQueryController() {
        // start connection with db
        this.dbManager = new DbManager();
        DbManager.queryExecutor = new DdlQueryExecutor();
        this.dbManager.connectToDB();

        // get tables' names
        TableView<List<String>> tableView;
        try {
            tableView = this.dbManager.executeQuery("SHOW TABLES");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // add all the names to the combo box
        this.namesOfTables = FXCollections.observableArrayList();
        int rows = tableView.getItems().size();
        TableColumn<List<String>, ?> col = tableView.getColumns().get(0);
        for (int i = 0; i < rows; i++) {
            String tableName = (String) col.getCellObservableValue(i).getValue();
            this.namesOfTables.add(tableName);
        }

        this.isComboBoxClicked = false;
        this.checkBoxes = new ArrayList<CheckBox>();
    }

    /**
     * user clicked the combo box dropdown.
     *
     * @param mouseEvent
     */
    public void dropdownClick(MouseEvent mouseEvent) {
        if (!this.isComboBoxClicked) {
            this.isComboBoxClicked = true;
            tablesNames.getItems().addAll(this.namesOfTables);
        }
    }

    /**
     * user selected item from the combo box.
     * show all the columns associated with the selected table's name.
     *
     * @param event event.
     */
    public void selectedItem(ActionEvent event) {
        // get selected table's name
        String tableName = tablesNames.getSelectionModel().getSelectedItem();

        // get columns of that table
        DbManager.queryExecutor = new DmlQueryExecutor();
        TableView<List<String>> tableView;
        try {
            tableView = this.dbManager.executeQuery("SELECT * FROM " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ObservableList<TableColumn<List<String>, ?>> columns = tableView.getColumns();
        int columnsCount = columns.size();
        javafx.geometry.Insets insets = new Insets(0, 0, 0, 10);

        // add the check boxes
        for (int i = 0; i < columnsCount; i++) {
            // add to gui
            String columnName = columns.get(i).textProperty().getValue();
            CheckBox checkBox = new CheckBox(columnName);
            checkBox.setPadding(insets);

            // save for later use
            this.checkBoxes.add(checkBox);
            columnsContainer.getChildren().add(checkBox);
        }
    }

    /**
     * user clicked the submit button.
     * generate the query and execute it.
     *
     * @param mouseEvent event.
     */
    public void submitClick(MouseEvent mouseEvent) {
        notesField.setText("");
        StringBuilder query = new StringBuilder();

        // insert columns
        query.append("SELECT ");
        for (CheckBox checkbox : checkBoxes) {
            if (checkbox.isSelected()) {
                query.append(checkbox.getText()).append(", ");
            }
        }
        query.deleteCharAt(query.lastIndexOf(","));

        // table's name
        query.append("FROM ").append(tablesNames.getSelectionModel().getSelectedItem());

        if (!whereSection.getText().isEmpty()) {
            query.append(" WHERE ").append(whereSection.getText());
        }

        // execute query
        TableView<List<String>> tableView;
        try {
            tableView = this.dbManager.executeQuery(query.toString());
        } catch (Exception e) {
            String msgErr;
            if (e.getMessage().contains("syntax")) {
                msgErr = "WRONG QUERY STRUCTURE:\n" + e.getMessage();
            } else {
                msgErr = "LOGICAL ERROR:\n" + e.getMessage();
            }
            notesField.setText(msgErr);
            return;
        }

        // show result table
        Scene scene = new Scene(tableView, 400, 500);
        currentStage.setScene(scene);
        currentStage.show();
    }
}
