package com.company.akeninbaev.service;

import com.company.akeninbaev.model.Game;
import com.j256.ormlite.dao.Dao;

public class GameService extends AbstractService<Game>{
    public GameService(Dao<Game, Integer> dao) {
        super(dao);
    }
}
