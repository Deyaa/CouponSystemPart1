package com.bhp.dbdao;

import com.bhp.beans.Company;
import com.bhp.dao.CompaniesDAO;
import com.bhp.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private static final String QUERY_INSERT    = "INSERT INTO `bhp-coupon-system`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String QUERY_UPDATE    = "UPDATE `bhp-coupon-system`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE    = "DELETE FROM `bhp-coupon-system`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE   = "SELECT * FROM `bhp-coupon-system`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL   = "SELECT * FROM `bhp-coupon-system`.`companies`";
    private static final String QUERY_IS_EXISTS = "SELECT count(*) FROM `bhp-coupon-system`.`companies` WHERE (`email` = ?) and (`password` = ?);";
    private static final String QUERY_COMPANY_NAME_EXIST = "SELECT count(*) FROM `bhp-coupon-system`.`companies` WHERE (`name` = ?);";
    private static final String QUERY_COMPANY_EMAIL_EXIST = "SELECT count(*) FROM `bhp-coupon-system`.`companies` WHERE (`email` = ?);";
    private static final String QUERY_GET_COMPANY_ID = "SELECT * FROM `bhp-coupon-system`.`companies` WHERE (`email` = ?) and (`password` = ?);";

    @Override
    public void add(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        DBUtils.runQuery(QUERY_INSERT, map);
    }

    @Override
    public void update(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());
        DBUtils.runQuery(QUERY_UPDATE, map);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        DBUtils.runQuery(QUERY_DELETE, map);
    }

    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companies = new ArrayList<>();

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            Company company = new Company(id, name, email, password);
            companies.add(company);
        }

        return companies;
    }

    @Override
    public Company getOne(Integer id) throws SQLException {
        Company company = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE, map);
        resultSet.next();

        //int companyId = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String email = resultSet.getString(3);
        String password = resultSet.getString(4);
        company = new Company(id, name, email, password);
        return company;
    }

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_EXISTS, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public boolean isCompanyNameExist(String name) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_COMPANY_NAME_EXIST, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public boolean isCompanyEmailExist(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_COMPANY_EMAIL_EXIST, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public int getCompanyIdByEmailAndPassword(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_COMPANY_ID, map);
        resultSet.next();

        return resultSet.getInt(1);
    }

    @Override
    public boolean isCompanyIdExist(int companyId) throws SQLException {
        Company company = getOne(companyId);
        if(company != null){
            return true;
        }
        return false;
    }
}
