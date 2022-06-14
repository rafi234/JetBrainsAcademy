package carsharing;

import carsharing.database.H2jdbc;
import carsharing.menu.Menu;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            H2jdbc.getConn();
            H2jdbc.createTables();
            new Menu().getMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        H2jdbc.closeConnection();
    }
}