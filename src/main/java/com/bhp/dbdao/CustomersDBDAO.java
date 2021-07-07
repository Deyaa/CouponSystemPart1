package com.bhp.dbdao;

import com.bhp.beans.Customer;
import com.bhp.dao.CustomersDAO;
import com.bhp.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {

    private static final String QUERY_INSERT    = "INSERT INTO `bhp-coupon-system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE    = "UPDATE `bhp-coupon-system`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE    = "DELETE FROM `bhp-coupon-system`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE   = "SELECT * FROM `bhp-coupon-system`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL   = "SELECT * FROM `bhp-coupon-system`.`customers`";
    private static final String QUERY_IS_EXISTS = "SELECT count(*) FROM `bhp-coupon-system`.`customers` WHERE (`email` = ?) and (`password` = ?);";
    private static final String QUERY_GET_CUSTOMER_ID = "SELECT * FROM `bhp-coupon-system`.`customers` WHERE (`email` = ?) and (`password` = ?);";
    private static final String QUERY_CUSTOMER_EMAIL_EXIST = "SELECT count(*) FROM `bhp-coupon-system`.`customers` WHERE (`email` = ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID = "DELETE FROM `bhp-coupon-system`.`customers_coupons` WHERE (`customer_id` = ?);";

    @Override
    public void add(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        DBUtils.runQuery(QUERY_INSERT, map);
    }

    @Override
    public void update(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customer.getId());
        DBUtils.runQuery(QUERY_UPDATE, map);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        DBUtils.runQuery(QUERY_DELETE, map);
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            Customer customer = new Customer(id, firstName, lastName, email, password);
            customers.add(customer);
        }

        return customers;
    }

    @Override
    public Customer getOne(Integer id) throws SQLException {
        Customer customer = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE, map);
        resultSet.next();

        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5);
        customer = new Customer(id, firstName, lastName, email, password);
        return customer;
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_EXISTS, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_CUSTOMER_ID, map);
        resultSet.next();

        return resultSet.getInt(1);
    }

    @Override
    public boolean isCustomerEmailExist(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_CUSTOMER_EMAIL_EXIST, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public void deleteCustomerVsCouponByCustomerId(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID, map);
    }
}
