package com.company.akeninbaev.serializers;

import com.company.akeninbaev.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(User.FILED_ID, user.getId());
        gen.writeStringField(User.FIELD_FIRST_NAME, user.getFirstName());
        gen.writeStringField(User.FIELD_LAST_NAME, user.getLastName());
        gen.writeStringField(User.FIELD_NICKNAME, user.getNickname());
        gen.writeStringField(User.FIELD_EMAIL, user.getEmail());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        gen.writeStringField(User.FIELD_BIRTHDAY, user.getBirthday().format(formatter));
        gen.writeStringField(User.FIELD_USER_ROLE, String.valueOf(user.getUserRole()));
        gen.writeEndObject();
    }
}
