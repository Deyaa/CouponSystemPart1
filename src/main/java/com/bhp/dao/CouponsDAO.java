package com.bhp.dao;

import com.bhp.beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO extends ClassicDAO<Coupon,Integer> {
    List<Coupon> getAllByCompanyId(int companyId) throws SQLException;
    void deleteCustomerVsCouponByCouponId(int id) throws SQLException;
    List<Coupon> getAllByCompanyAndCategoryId(int companyId, int categoryId) throws SQLException;
    List<Coupon> getAllByCompanyAndPrice(int companyId, double maxPrice) throws SQLException;
    boolean isCompanyCouponTitleExist(int companyId, String title) throws SQLException;
    boolean isCouponIdExist(int id) throws SQLException;
    List<Coupon> getExpiredCoupons() throws SQLException;
}
