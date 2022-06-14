package carsharing.repository;

import carsharing.database.H2jdbc;
import carsharing.models.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name FROM company");;
        List<Company> companies = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            companies.add(new Company(id, name));
        }
        stmt.close();
        return companies;
    }

    @Override
    public void updateCompany(String companyName) throws SQLException {
        Connection conn = H2jdbc.getConn();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO COMPANY (NAME) VALUES (\'" + companyName + "\')");
        stmt.close();
    }

    @Override
    public String getNameById(int id) throws SQLException {
        Statement stmt = H2jdbc.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM COMPANY WHERE ID=" + id);
        String result = "";
        while (rs.next()) {
            result = rs.getString("name");
        }
        stmt.close();
        return result;
    }
}
