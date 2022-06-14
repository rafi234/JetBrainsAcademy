package carsharing.menu;

import carsharing.models.Company;
import carsharing.repository.CompanyDao;
import carsharing.repository.CompanyDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ManagerMenu {

    private static final CompanyDao companyDao = new CompanyDaoImpl();

    public static void logInAsManager() throws SQLException {
        int input;
        while (true) {
            printManagerMenu();
            input = Menu.sc.nextInt();
            switch (input) {
                case 1:
                    if (printAllCompanies() != 0)
                        CompanyMenu.getCompanyMenu(new Scanner(System.in).nextInt());
                    break;
                case 2:
                    createCompany();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    public static int printAllCompanies() throws SQLException {
        List<Company> companies = companyDao.getAllCompanies();
        if (companies.size() == 0) {
            System.out.println("\nThe company list is empty!");
            return 0;
        } else {
            System.out.println("\nChose a company");
        }
        for (Company company : companies) {
            System.out.println(company);
        }
        System.out.println("0. Back");
        return companies.size();
    }

    private static void createCompany() throws SQLException {
        System.out.println("\nEnter the company name:");
        String companyName = new Scanner(System.in).nextLine();
        companyDao.updateCompany(companyName);
    }

    private static void printManagerMenu() {
        System.out.println(
                "\n1. Company list\n" +
                        "2. Create a company\n" +
                        "0. Back"
        );
    }
}
