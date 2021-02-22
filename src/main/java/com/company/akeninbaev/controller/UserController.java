package com.company.akeninbaev.controller;

import com.company.akeninbaev.model.User;
import com.company.akeninbaev.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController extends AbstractController<User>{
    public UserController(Service<User> service, ObjectMapper objectMapper) {
        super(service, objectMapper, User.class);
    }
}
