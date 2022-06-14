package carsharing.database;

import java.sql.*;

public class H2jdbc {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private static final String CREATE_COMPANY_SQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR UNIQUE NOT NULL)";
    private static final String CREATE_CAR_SQL = "CREATE TABLE IF NOT EXISTS CAR " +
            "(ID INTEGER AUTO_INCREMENT PRIMARY KEY," +
            " NAME VARCHAR UNIQUE NOT NULL," +
            " COMPANY_ID INT NOT NULL," +
            " CONSTRAINT fk_com_car FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

    private static final String CREATE_CUSTOMER_SQL = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL, " +
            "RENTED_CAR_ID INT, " +
            "CONSTRAINT FK_CUST_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

    private static final String RESET_PRIMARY_KEY = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
    private static Statement stmt;
    private static Connection conn;

    private H2jdbc() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true);

    }

    public static void createTables() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute(CREATE_COMPANY_SQL);
        stmt.execute(CREATE_CAR_SQL);
        stmt.execute(CREATE_CUSTOMER_SQL);
        stmt.execute(RESET_PRIMARY_KEY);
        stmt.close();
    }

    public static void dropIfExists() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("DROP TABLE IF EXISTS CUSTOMER");
        stmt.execute("DROP TABLE IF EXISTS CAR");
        stmt.execute("DROP TABLE IF EXISTS COMPANY");
        stmt.close();
    }

    public static Connection getConn() throws SQLException {
        if (conn == null) {
            new H2jdbc();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
