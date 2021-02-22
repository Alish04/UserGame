package com.company.akeninbaev.service;

import com.company.akeninbaev.exception.ApplicationException;
import com.company.akeninbaev.model.Model;
import com.company.akeninbaev.model.User;
import com.j256.ormlite.dao.Dao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public interface Service<T extends Model> {
    Dao<T, Integer> dao();

    default List<T> findAll() {
        try {
            return dao().queryForAll();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

    default T findById(int id) {
        try {
            return dao().queryForId(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

    default List<T> findBy(String columnName, Object value) {
        try {
            return dao().queryForEq(columnName, value);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

    default void save(T model) {
        try {
            dao().create(model);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

    default void update(T model) {
        try {
            dao().update(model);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

    default void delete (T model) {
        try {
            dao().delete(model);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("SQL Exception occurred", throwable);
        }
    }

}
