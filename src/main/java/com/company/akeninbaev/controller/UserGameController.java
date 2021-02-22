package com.company.akeninbaev.controller;

import com.company.akeninbaev.model.Game;
import com.company.akeninbaev.model.User;
import com.company.akeninbaev.model.UserGame;
import com.company.akeninbaev.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class UserGameController extends AbstractController<UserGame>{
    private Dao<User, Integer> userDao;
    private Dao<UserGame, Integer> libraryDao;

    public UserGameController(Service<UserGame> service, ObjectMapper objectMapper) {
        super(service, objectMapper, UserGame.class);
    }
    
    @Override
    public void getAll(Context context) {
        try{
            String password = context.basicAuthCredentials().getPassword();
            String username = context.basicAuthCredentials().getUsername();
            List<User> users = userDao.queryForAll();
            List<Game> games = new ArrayList<>();
            for(int i = 0; i < users.size(); i++){
                User user = users.get(i);
                if(user.getPassword().equals(password) && user.getNickname().equals(username)){
                    List<UserGame> gamesId = libraryDao.queryBuilder().where().eq("User_Id", user.getId()).query();
                    for(int j = 0; j < gamesId.size(); j++){
                        games.add(gamesId.get(j).getGame());
                    }
                }
            }
            context.result(getObjectMapper().writeValueAsString(games));
        }catch (Exception e){
            e.printStackTrace();
            context.status(500);
        }
    }

    @Override
    public void post(Context context) {
        try{
            UserGame library = getObjectMapper().readValue(context.body(), getClazz());
            getService().save(library);
            UserGame saved = getService().findById(library.getId());
            context.result(getObjectMapper().writeValueAsString(saved));
            context.status(201);
        } catch (Exception e) {
            e.printStackTrace();
            context.status(400);
        }
    }
}
