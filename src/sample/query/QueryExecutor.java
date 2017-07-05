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
import java.util.List;

/**
 * object that execute some query.
 */
public interface QueryExecutor {

    /**
     * execute query.
     *
     * @param connection connection to db.
     * @param query      string representing the query to send to the db.
     * @return result table.
     * @throws SQLException problem in sql.
     * @throws IOException  problem in reading from file.
     */
    TableView<List<String>> executeQuery(Connection connection, String query) throws SQLException, IOException;
}
