package carsharing.repository;

import carsharing.database.H2jdbc;
import carsharing.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public void updateCustomer(String name) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        stmt.executeUpdate("INSERT INTO CUSTOMER (NAME) VALUES (\'" + name + "\')");
        stmt.close();
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name, rented_car_id FROM CUSTOMER");;
        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int customer_car_id = rs.getInt("rented_car_id");
            customers.add(new Customer(id, name, customer_car_id));
        }
        stmt.close();
        return customers;
    }

    @Override
    public void rentCar(int carId, Customer customer) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        stmt.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = " + carId +
                " WHERE ID = " + customer.getId());
        stmt.close();
    }

    @Override
    public Customer getCustomerById(int id) throws SQLException {
        Customer customer = null;
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT id, name, rented_car_id from CUSTOMER WHERE id = " + id
        );
        while (rs.next()) {
            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("rented_car_id")
            );
        }
        stmt.close();
        return customer;
    }

    @Override
    public int getCustomerCarId(int id) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT customer_car_id from customer where id = " + id);
        rs.next();
        int rentedCarId = rs.getInt("rented_car_id");
        stmt.close();
        return rentedCarId;
    }

    @Override
    public void returnCar(int carId, Customer customer) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        stmt.execute("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customer.getId());
        stmt.close();
    }
}
