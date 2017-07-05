/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample.query;

import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * dml query.
 */
public class DmlQueryExecutor extends BaseQueryExecutor {

    /**
     * execute dml query.
     *
     * @param connection connection to db.
     * @param query      string representing the query to send to the db.
     * @return result table.
     * @throws SQLException sql problem.
     */
    @Override
    public TableView<List<String>> executeQuery(Connection connection, String query) throws SQLException {
        Statement s = connection.createStatement();
        if (query.contains("SELECT")) {
            ResultSet result = s.executeQuery(query);
            //return the table.
            return getResultTable(result);
        } else {
            s.executeUpdate(query);
            return null;
        }
    }
}
