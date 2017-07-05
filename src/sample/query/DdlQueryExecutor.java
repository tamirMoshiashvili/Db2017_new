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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * ddl query.
 */
public class DdlQueryExecutor extends BaseQueryExecutor {

    /**
     * execute ddl query.
     *
     * @param connection connection to db.
     * @param query      string representing the query to send to the db.
     * @return result table.
     * @throws SQLException sql problem.
     */
    @Override
    public TableView<List<String>> executeQuery(Connection connection, String query) throws SQLException {
        Statement s = connection.createStatement();
        if (query.contains("DESCRIBE") || query.contains("SHOW")) {
            return getResultTable(s.executeQuery(query));
        } else {
            s.executeUpdate(query);
            return null;
        }
    }
}
