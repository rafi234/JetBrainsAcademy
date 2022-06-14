package carsharing.menu;

import carsharing.models.Car;
import carsharing.repository.CarDao;
import carsharing.repository.CarDaoImpl;
import carsharing.repository.CompanyDao;
import carsharing.repository.CompanyDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CompanyMenu {

    private static final CompanyDao companyDao = new CompanyDaoImpl();
    private static final CarDao carDao = new CarDaoImpl();

    public static void getCompanyMenu(int companyId) throws SQLException {
        if (companyId == 0) {
            return;
        }
        String companyName = companyDao.getNameById(companyId);
        int input;
        while (true) {
            printManagerMenu(companyName);
            input = Menu.sc.nextInt();
            switch (input) {
                case 1:
                    printAllCars(companyId);
                    break;
                case 2:
                    createCar(companyId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private static void printAllCars(int companyId) throws SQLException {
        List<Car> cars = carDao.getAllCarsFromOneCompany(companyId);
        if (cars.size() == 0) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
        }
        int i = 1;
        for (Car car : cars) {
            System.out.println(i++ + ". " + car.getName());
        }
    }

    private static void createCar(int companyId) throws SQLException {
        System.out.println("\nEnter the car name:");
        String carName = new Scanner(System.in).nextLine();
        carDao.updateCars(carName, companyId);
        System.out.println("The car was added!");
    }

    private static void printManagerMenu(String companyName) {
        System.out.println(
                "\n'" + companyName + "' company\n" +
                        "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back"
        );
    }
}
