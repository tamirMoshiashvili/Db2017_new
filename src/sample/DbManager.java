/**
 * David Samuelson 208788851 89-281-02
 * Eden Shuker 208991406 89-281-02
 * Tamir Moshiashvili 316131259 89-281-02
 * Haim Rubinshtein 203405286 89-281-02
 */

package sample;

import javafx.scene.control.TableView;
import sample.query.QueryExecutor;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Object that manage the connection with the DB.
 */
public class DbManager {

    private Connection connection;

    /**
     * Object to perform the query.
     */
    public static QueryExecutor queryExecutor;

    /**
     * connect to the DB.
     */
    public void connectToDB() {
        String user;
        String pass;
        String dbDriver;

        // load driver
        try {
            String dbClass = "com.mysql.jdbc.Driver";
            Class.forName(dbClass);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return;
        }

        // get the conf file
        BufferedReader reader;
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            reader = new BufferedReader(new InputStreamReader
                    (new FileInputStream(new File("conf.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // get the info from file
        try {
            dbDriver = reader.readLine();
            user = reader.readLine();
            pass = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // connect
        try {
            this.connection = DriverManager.getConnection(dbDriver, user, pass);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    /**
     * execute the given query.
     *
     * @param query string representing the query to execute.
     * @return result table according to the query.
     * @throws SQLException sql problem.
     * @throws IOException  file problem.
     */
    public TableView<List<String>> executeQuery(String query) throws SQLException, IOException {
        return queryExecutor.executeQuery(this.connection, query);
    }
}
