package com.bhp.dbdao;

import com.bhp.dao.CustomerCouponDAO;
import com.bhp.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomerCouponDBDAO implements CustomerCouponDAO {

    private static final String QUERY_INSERT_COUPON_PURCHASE = "INSERT INTO `bhp-coupon-system`.`customers_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE = "DELETE FROM `bhp-coupon-system`.`customers_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);";
    private static final String QUERY_IS_EXISTS_COUPON = "SELECT COUNT(*) FROM `bhp-coupon-system`.`customers_coupons` WHERE (`coupon_id` = ?);";

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runQuery(QUERY_INSERT_COUPON_PURCHASE, map);
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE, map);
    }

    @Override
    public boolean isPurchase(int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_EXISTS_COUPON, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }
}
