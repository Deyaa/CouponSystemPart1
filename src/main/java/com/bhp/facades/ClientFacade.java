package com.bhp.facades;

import com.bhp.dao.CompaniesDAO;
import com.bhp.dao.CouponsDAO;
import com.bhp.dao.CustomerCouponDAO;
import com.bhp.dao.CustomersDAO;
import com.bhp.dbdao.CompaniesDBDAO;
import com.bhp.dbdao.CouponDBDAO;
import com.bhp.dbdao.CustomerCouponDBDAO;
import com.bhp.dbdao.CustomersDBDAO;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customerDAO;
    protected CouponsDAO couponsDAO;
    protected CustomerCouponDAO customerCouponDAO;

    public ClientFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customerDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponDBDAO();
        this.customerCouponDAO = new CustomerCouponDBDAO();
    }

    public abstract boolean login(String email,String password) throws SQLException;
}
