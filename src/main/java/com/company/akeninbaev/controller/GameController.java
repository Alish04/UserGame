package com.company.akeninbaev.controller;

import com.company.akeninbaev.model.Game;
import com.company.akeninbaev.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameController extends AbstractController<Game>{
    public GameController(Service<Game> service, ObjectMapper objectMapper) {
        super(service, objectMapper, Game.class);
    }
}
