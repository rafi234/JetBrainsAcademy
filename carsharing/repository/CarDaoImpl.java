package carsharing.repository;

import carsharing.database.H2jdbc;
import carsharing.models.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public List<Car> getAllCarsFromOneCompany(int companyId) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT id, name FROM CAR" +
                        " WHERE COMPANY_ID=" + companyId
        );
        List<Car> cars = getCars(rs);
        stmt.close();
        return cars;
    }

    @Override
    public List<Car> getAllAvailableCarsFromOneCompany(int companyId) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT car.id as id , car.name as name FROM CAR " +
                        "LEFT JOIN CUSTOMER ON CAR.ID=CUSTOMER.RENTED_CAR_ID " +
                        "WHERE COMPANY_ID=" + companyId + " and RENTED_CAR_ID IS NULL"
        );
        List<Car> cars = getCars(rs);
        stmt.close();
        return cars;
    }

    @Override
    public void updateCars(String carName, int companyId) throws SQLException {
        Connection conn = H2jdbc.getConn();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(
                "INSERT INTO CAR (NAME, COMPANY_ID) " +
                        "VALUES (\'" + carName + "', '" + companyId + "\')"
        );
        stmt.close();
    }

    @Override
    public Car getCarById(int id) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name, COMPANY_ID FROM CAR WHERE ID = " + id);
        rs.next();
        int carId = rs.getInt("id");
        String name = rs.getString("name");
        int companyId = rs.getInt("COMPANY_ID");
        stmt.close();
        return new Car(carId, name, companyId);
    }

    @Override
    public int getIdByName(String name) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM CAR WHERE NAME = '" + name + "'");
        rs.next();
        int carId = rs.getInt("id");
        stmt.close();
        return carId;
    }

    private List<Car> getCars(ResultSet rs) throws SQLException {
        List<Car> cars = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            cars.add(new Car(id, name));
        }
        return cars;
    }
}
