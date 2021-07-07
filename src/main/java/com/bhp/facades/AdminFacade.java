package com.bhp.facades;

import com.bhp.beans.Company;
import com.bhp.beans.Coupon;
import com.bhp.beans.Customer;
import com.bhp.exceptions.CouponSystemException;
import com.bhp.exceptions.ErrorMessage;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade{

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws CouponSystemException, SQLException {
        if(this.companiesDAO.isCompanyNameExist(company.getName())){
            throw new CouponSystemException(ErrorMessage.COMPANY_NAME_EXISTS);
        }
        if(this.companiesDAO.isCompanyEmailExist(company.getEmail())){
            throw new CouponSystemException(ErrorMessage.COMPANY_EMAIL_EXISTS);
        }
        this.companiesDAO.add(company);
    }

    public void updateCompany(Company company) throws CouponSystemException, SQLException {
        Company companyFromDB = this.companiesDAO.getOne(company.getId());
        if(companyFromDB == null){
            throw new CouponSystemException(ErrorMessage.COMPANY_ID_NOT_EXISTS);
        }
        if(companyFromDB.getName().equals(company.getName())){
            throw new CouponSystemException(ErrorMessage.UPDATE_COMPANY_NAME_EXISTS);
        }
        this.companiesDAO.update(company);
    }

    public void deleteCompany(int companyId) throws SQLException {
        List<Coupon> companyCoupons = this.couponsDAO.getAllByCompanyId(companyId);
        for (Coupon c: companyCoupons) {
            this.couponsDAO.deleteCustomerVsCouponByCouponId(c.getId());
            this.couponsDAO.delete(c.getId());
        }
        this.companiesDAO.delete(companyId);
    }

    public List<Company> getAllCompanies() throws SQLException {
        return this.companiesDAO.getAll();
    }

    public Company getOneCompany(int companyId) throws SQLException {
        return this.companiesDAO.getOne(companyId);
    }

    public void addCustomer(Customer customer) throws SQLException, CouponSystemException {
        if(this.customerDAO.isCustomerEmailExist(customer.getEmail())){
            throw new CouponSystemException(ErrorMessage.CUSTOMER_EMAIL_EXISTS);
        }
        this.customerDAO.add(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException, CouponSystemException {
        Customer customerFromDB = this.customerDAO.getOne(customer.getId());
        if(customerFromDB == null){
            throw new CouponSystemException(ErrorMessage.CUSTOMER_ID_NOT_EXISTS);
        }
        this.customerDAO.update(customer);
    }

    public void deleteCustomer(int customerId) throws SQLException {
        this.customerDAO.deleteCustomerVsCouponByCustomerId(customerId);
        this.customerDAO.delete(customerId);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return this.customerDAO.getAll();
    }

    public Customer getOneCustomer(int customerId) throws SQLException {
        return this.customerDAO.getOne(customerId);
    }
}
