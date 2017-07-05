/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample.query;

import javafx.scene.control.TableView;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

/**
 * simple query.
 */
public class ScriptQueryExecutor extends BaseQueryExecutor {

    /**
     * execute ddl query.
     *
     * @param connection connection to db.
     * @param path       string representing the path to script file.
     * @return result table.
     * @throws SQLException sql problem.
     */
    @Override
    public TableView<List<String>> executeQuery(Connection connection, String path) throws SQLException, IOException {
        TableView<List<String>> tableView = null;
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(";");
        ResultSet result;
        // iterate script
        while (scanner.hasNext()) {
            String line = scanner.next();
            Statement s = connection.createStatement();
            if (line.contains("SELECT") || line.contains("DESCRIBE") || line.contains("SHOW")) {
                result = s.executeQuery(line);
                TableView<List<String>> resultTable = getResultTable(result);
                if (resultTable != null) {
                    tableView = resultTable;
                }
            } else {
                s.executeUpdate(line);
            }
        }
        return tableView;
    }
}
