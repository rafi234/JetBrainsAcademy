package carsharing.menu;

import carsharing.models.Car;
import carsharing.models.Customer;
import carsharing.repository.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {

    private static final CompanyDao companyDao = new CompanyDaoImpl();
    private static final CustomerDao customerDao = new CustomerDaoImpl();
    private static final CarDao carDao = new CarDaoImpl();
    private static Customer customer;

    public static void getCustomerMenu(int customerId) throws SQLException {
        customer = customerDao.getCustomerById(customerId);
        int input;
        while (true) {
            printCustomerMenu();
            input = Menu.sc.nextInt();
            switch (input) {
                case 1:
                    rentACar();
                    break;
                case 2:
                    returnRentedCar();
                    break;
                case 3:
                    myRentedCar();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private static void myRentedCar() throws SQLException {
        System.out.println();
        if (!customer.isCarRented()) {
            System.out.println("You didn't rent a car!");
            return;
        }
        Car car = carDao.getCarById(customer.getCustomer_car_id());
        String companyName = companyDao.getNameById(car.getFk_car_comp());
        System.out.println(
                "Your rented car:\n" + car.getName() +
                "\nCompany:\n" + companyName
        );
    }

    private static void returnRentedCar() throws SQLException {
        if (!customer.isCarRented()) {
            System.out.println("You didn't rent a car!");
            return;
        }
        customer.setCustomer_car_id(0);
        customerDao.returnCar(customer.getId(), customer);
        System.out.println("You've returned a rented car!");
    }

    private static void rentACar() throws SQLException {
        if (customer.isCarRented()) {
            System.out.println("You've already rented a car!");
            return;
        }
        if (ManagerMenu.printAllCompanies() == 0) {
            return;
        }
        int companyId = new Scanner(System.in).nextInt();
        String companyName = companyDao.getNameById(companyId);
        List<Car> cars = carDao.getAllAvailableCarsFromOneCompany(companyId);
        if (cars.size() == 0) {
            System.out.println("No available cars in the '" + companyName + "' company");
            return;
        }
        int i = 1;
        for (Car car : cars) {
            System.out.println(i++ + ". " + car.getName());
        }
        System.out.println("0. Back");
        int carId = new Scanner(System.in).nextInt();
        if (carId == 0) {
            return;
        }
        String carName = cars.get(carId - 1).getName();
        carId = carDao.getIdByName(carName);
        customerDao.rentCar(carId, customer);
        System.out.println("You rented '" + carName + "'");
        customer.setCustomer_car_id(carId);
    }

    private static void printCustomerMenu() {
        System.out.println("\n1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }
}
