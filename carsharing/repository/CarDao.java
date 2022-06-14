package carsharing.repository;

import carsharing.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    public List<Car> getAllCarsFromOneCompany(int companyId) throws SQLException;

    public List<Car> getAllAvailableCarsFromOneCompany(int companyId) throws SQLException;

    public void updateCars(String carName, int companyId) throws SQLException;

    public Car getCarById(int id) throws SQLException;

    public int getIdByName(String name) throws SQLException;
}
