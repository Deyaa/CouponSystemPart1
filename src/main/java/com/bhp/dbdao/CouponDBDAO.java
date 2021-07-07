package com.bhp.dbdao;

import com.bhp.beans.Category;
import com.bhp.beans.Coupon;
import com.bhp.dao.CouponsDAO;
import com.bhp.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CouponDBDAO implements CouponsDAO {

    private static final String QUERY_INSERT    = "INSERT INTO `bhp-coupon-system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE    = "UPDATE `bhp-coupon-system`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE    = "DELETE FROM `bhp-coupon-system`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE   = "SELECT * FROM `bhp-coupon-system`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL   = "SELECT * FROM `bhp-coupon-system`.`coupons`";
    private static final String QUERY_GET_ALL_BY_COMPANY_ID   = "SELECT * FROM `bhp-coupon-system`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID = "DELETE FROM `bhp-coupon-system`.`customers_coupons` WHERE (`coupon_id` = ?);";
    private static final String QUERY_GET_ALL_BY_COMPANY_ID_COMPANY_ID   = "SELECT * FROM `bhp-coupon-system`.`coupons` WHERE (`company_id` = ?) and (`category_id` = ?);";
    private static final String QUERY_GET_ALL_BY_COMPANY_ID_PRICE   = "SELECT * FROM `bhp-coupon-system`.`coupons` WHERE (`company_id` = ?) and (`price` < ?);";
    private static final String QUERY_EXPIRED_COUPON = "SELECT * FROM `bhp-coupon-system`.coupons where end_date < CURDATE();";
    private static final String QUERY_GET_BY_COMPANY_ID_And_Title   = "SELECT count(*) FROM `bhp-coupon-system`.`coupons` WHERE (`company_id` = ?) and (title = ?);";

    @Override
    public void add(Coupon coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategory().value);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        DBUtils.runQuery(QUERY_INSERT, map);
    }

    @Override
    public void update(Coupon coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategory().value);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        map.put(10, coupon.getId());
        DBUtils.runQuery(QUERY_UPDATE, map);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        DBUtils.runQuery(QUERY_DELETE, map);
    }

    @Override
    public List<Coupon> getAll() throws SQLException {
        List<Coupon> coupons = prepareData(QUERY_GET_ALL, null);
        return coupons;
    }

    @Override
    public Coupon getOne(Integer id) throws SQLException {
        Coupon coupon = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE, map);
        resultSet.next();

        int companyId = resultSet.getInt(2);
        Category categoryName = Category.values()[resultSet.getInt(3) - 1];
        String title = resultSet.getString(4);
        String description = resultSet.getString(5);
        LocalDate startDate = resultSet.getDate(6).toLocalDate();
        LocalDate endDate = resultSet.getDate(7).toLocalDate();
        int amount = resultSet.getInt(8);
        double price = resultSet.getDouble(9);
        String image = resultSet.getString(10);

        coupon = new Coupon(id, companyId, categoryName , title, description, startDate, endDate,
                amount, price, image);
        return coupon;
    }

    @Override
    public List<Coupon> getAllByCompanyId(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);

        List<Coupon> coupons = prepareData(QUERY_GET_ALL_BY_COMPANY_ID, map);
        return coupons;
    }

    @Override
    public void deleteCustomerVsCouponByCouponId(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID, map);
    }

    @Override
    public List<Coupon> getAllByCompanyAndCategoryId(int companyId, int categoryId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, categoryId);

        List<Coupon> coupons = prepareData(QUERY_GET_ALL_BY_COMPANY_ID_COMPANY_ID, map);
        return coupons;
    }

    @Override
    public List<Coupon> getAllByCompanyAndPrice(int companyId, double maxPrice) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, maxPrice);

        List<Coupon> coupons = prepareData(QUERY_GET_ALL_BY_COMPANY_ID_PRICE, map);
        return coupons;
    }

    @Override
    public boolean isCompanyCouponTitleExist(int companyId, String title) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, title);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_BY_COMPANY_ID_And_Title, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    @Override
    public boolean isCouponIdExist(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE, map);
        resultSet.next();

        return (resultSet.getInt(1) == 1);
    }

    public List<Coupon> getExpiredCoupons() throws SQLException {
        List<Coupon> coupons = prepareData(QUERY_EXPIRED_COUPON, null);
        return coupons;
    }

    private List<Coupon> prepareData(String queryToRun, Map<Integer, Object> map) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = null;
        if(map != null) {
            resultSet = DBUtils.runQueryWithResults(queryToRun, map);
        } else {
            resultSet = DBUtils.runQueryWithResults(queryToRun);
        }
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int companyId = resultSet.getInt(2);
            Category categoryName = Category.values()[resultSet.getInt(3) - 1];
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            LocalDate startDate = resultSet.getDate(6).toLocalDate();
            LocalDate endDate = resultSet.getDate(7).toLocalDate();
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupon coupon = new Coupon(id, companyId, categoryName, title, description, startDate, endDate,
                    amount, price, image);
            coupons.add(coupon);
        }
        return coupons;
    }
}
