package com.bhp.dao;

import java.sql.SQLException;

public interface CustomerCouponDAO {
    void addCouponPurchase(int customerId, int couponId) throws SQLException;
    void deleteCouponPurchase(int customerId, int couponId) throws SQLException;
    boolean isPurchase(int couponId) throws SQLException;
}
