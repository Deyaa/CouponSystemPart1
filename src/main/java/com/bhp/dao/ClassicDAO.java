package com.bhp.dao;

import java.sql.SQLException;
import java.util.List;

public interface ClassicDAO<T,I>{

    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete (I i) throws SQLException;
    List<T> getAll() throws SQLException;
    T getOne(I i) throws SQLException;
}
