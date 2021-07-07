package com.bhp.dbdao;

import com.bhp.beans.Category;
import com.bhp.dao.CategoriesDAO;
import com.bhp.utils.DBUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoriesDBDAO implements CategoriesDAO {

    private static final String QUERY_INSERT = "INSERT INTO `bhp-coupon-system`.`categories` (`name`) VALUES (?);";

    @Override
    public void addCategory(Category category) throws SQLException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, category.name());
        DBUtils.runQuery(QUERY_INSERT, map);
    }
}
