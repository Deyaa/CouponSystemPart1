package com.bhp.dao;

import com.bhp.beans.Category;

import java.sql.SQLException;

public interface CategoriesDAO {

    void addCategory(Category category) throws SQLException;

}
