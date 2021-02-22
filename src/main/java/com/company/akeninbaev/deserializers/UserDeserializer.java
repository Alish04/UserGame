package com.company.akeninbaev.deserializers;

import com.company.akeninbaev.model.User;
import com.company.akeninbaev.model.UserRole;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDeserializer extends StdDeserializer<User> {
    public UserDeserializer() {
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        int id = root.get(User.FILED_ID).asInt();
        String firstName = root.get(User.FIELD_FIRST_NAME).asText();
        String lastName = root.get(User.FIELD_LAST_NAME).asText();
        String nickname = root.get(User.FIELD_NICKNAME).asText();
        String email = root.get(User.FIELD_EMAIL).asText();
        String birthday = root.get(User.FIELD_BIRTHDAY).asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate bday = LocalDate.parse(birthday,formatter);
        String password = root.get(User.FIELD_PASSWORD).asText();
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        String role = root.get(User.FIELD_USER_ROLE).asText();
        role = role.toUpperCase();
        UserRole userRole = UserRole.valueOf(role);
        return new User(id, firstName, lastName, nickname, email, bday, hashed, userRole);
    }
}
