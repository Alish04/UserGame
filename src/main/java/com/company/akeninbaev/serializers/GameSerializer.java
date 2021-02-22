package com.company.akeninbaev.serializers;

import com.company.akeninbaev.model.Game;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class GameSerializer extends StdSerializer<Game> {
    public GameSerializer() {
        super(Game.class);
    }

    @Override
    public void serialize(Game game, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Game.FIELD_ID, game.getId());
        jsonGenerator.writeStringField(Game.FIELD_NAME, game.getName());
        jsonGenerator.writeStringField(Game.FIELD_DESCRIPTION, game.getDescription());
        jsonGenerator.writeNumberField(Game.FIELD_PRICE, game.getPrice());
        jsonGenerator.writeEndObject();
    }
}
