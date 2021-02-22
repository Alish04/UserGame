package com.company.akeninbaev.service;

import com.company.akeninbaev.model.UserGame;
import com.j256.ormlite.dao.Dao;

public class UserGameService extends AbstractService<UserGame>{
    public UserGameService(Dao<UserGame, Integer> dao) {
        super(dao);
    }
}
