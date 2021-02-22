package com.company.akeninbaev.deserializers;

import com.company.akeninbaev.model.Game;
import com.company.akeninbaev.model.User;
import com.company.akeninbaev.model.UserGame;
import com.company.akeninbaev.service.Service;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserGameDeserializer extends StdDeserializer<UserGame> {
    private final Service<User> userService;
    private final Service<Game> gameService;

    protected UserGameDeserializer(Service<User> userService, Service<Game> gameService) {
        super(UserGame.class);
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public UserGame deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        int id = root.get(UserGame.FIELD_ID).asInt();
        int userId = root.get(UserGame.FIELD_USER_ID).asInt();
        User user = userService.findById(userId);
        int gameId = root.get(UserGame.FIELD_GAME_ID).asInt();
        Game game = gameService.findById(gameId);
        return new UserGame(id, user, game);
    }
}
