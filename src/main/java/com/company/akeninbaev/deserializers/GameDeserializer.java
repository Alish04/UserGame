package com.company.akeninbaev.deserializers;

import com.company.akeninbaev.model.Game;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GameDeserializer extends StdDeserializer<Game> {
    public GameDeserializer() {
        super(Game.class);
    }

    @Override
    public Game deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        int id = root.get(Game.FIELD_ID).asInt();
        String name = root.get(Game.FIELD_NAME).asText();
        String description = root.get(Game.FIELD_DESCRIPTION).asText();
        Double price = root.get(Game.FIELD_PRICE).asDouble();
        return new Game(id, name, description, price);
    }
}
