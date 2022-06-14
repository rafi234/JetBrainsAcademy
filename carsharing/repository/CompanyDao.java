package carsharing.repository;

import carsharing.models.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies() throws SQLException;

    public void updateCompany(String companyName) throws SQLException;

    public String getNameById(int id) throws SQLException;
}
