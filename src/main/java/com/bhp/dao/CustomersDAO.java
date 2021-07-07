package com.bhp.dao;

import com.bhp.beans.Customer;

import java.sql.SQLException;

public interface CustomersDAO extends ClassicDAO<Customer, Integer> {

    boolean isCustomerExists(String email, String password) throws SQLException;
    int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException;
    boolean isCustomerEmailExist(String email) throws SQLException;
    void deleteCustomerVsCouponByCustomerId(int customerId) throws SQLException;
}
