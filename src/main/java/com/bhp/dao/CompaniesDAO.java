package com.bhp.dao;

import com.bhp.beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO extends ClassicDAO<Company,Integer> {

    boolean isCompanyExists(String email, String password) throws SQLException;
    boolean isCompanyNameExist(String name) throws SQLException;
    boolean isCompanyEmailExist(String email) throws SQLException;
    int getCompanyIdByEmailAndPassword(String email,String password) throws SQLException;
    boolean isCompanyIdExist(int companyId) throws SQLException;
}
