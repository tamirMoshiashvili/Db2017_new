/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample.query;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * abstract class for query.
 */
public abstract class BaseQueryExecutor implements QueryExecutor {

    /**
     * produce result table out of the given result object.
     *
     * @param resultSet result object from the db associated with query.
     * @return result table.
     * @throws SQLException problem in sql.
     */
    protected static TableView<List<String>> getResultTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        TableView tableView = new TableView();

        int columnCount = metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            final int j = i;
            TableColumn col = new TableColumn(metaData.getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tableView.getColumns().addAll(col);
        }

        while (resultSet.next()) {
            // iterate row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // iterate column
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }

        tableView.setItems(data);
        return tableView;
    }
}
