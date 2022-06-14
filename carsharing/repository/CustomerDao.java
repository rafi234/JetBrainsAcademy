package carsharing.repository;

import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    public void updateCustomer(String name) throws SQLException;

    public List<Customer> getAllCustomers() throws SQLException;

    public void rentCar(int carId, Customer customer) throws SQLException;

    public Customer getCustomerById(int id) throws SQLException;

    public int getCustomerCarId(int id) throws SQLException;

    public void returnCar(int carId, Customer customer) throws SQLException;
}
