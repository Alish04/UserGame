package com.company.akeninbaev.service;

import com.company.akeninbaev.model.Model;
import com.j256.ormlite.dao.Dao;

public class AbstractService<T extends Model> implements Service<T> {
    private final Dao<T, Integer> dao;

    public AbstractService(Dao<T, Integer> dao) {
        this.dao = dao;
    }

    @Override
    public Dao<T, Integer> dao() {
        return dao;
    }
}
