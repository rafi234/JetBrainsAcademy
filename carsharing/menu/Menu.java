package carsharing.menu;

import carsharing.models.Customer;
import carsharing.repository.CustomerDao;
import carsharing.repository.CustomerDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static Scanner sc = new Scanner(System.in);
    private static final CustomerDao customerDao = new CustomerDaoImpl();

    public void getMenu() throws SQLException {
        int input;
        while (true) {
            printMainMenu();
            input = sc.nextInt();
            switch (input) {
                case 1:
                    ManagerMenu.logInAsManager();
                    break;
                case 2:
                    logInAsCustomer();
                    break;
                case 3:
                    createCustomer();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private void createCustomer() throws SQLException {
        System.out.println("Enter the customer name:");
        String name = new Scanner(System.in).nextLine();
        customerDao.updateCustomer(name);
        System.out.println("The customer was added!");
    }

    private void logInAsCustomer() throws SQLException {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("\nThe customer list is empty!");
            return;
        } else {
            System.out.println("\nChose a customer:");
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println("0. Back");
        CustomerMenu.getCustomerMenu(new Scanner(System.in).nextInt());
    }

    private void printMainMenu() {
        System.out.println("\n1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
    }

}
