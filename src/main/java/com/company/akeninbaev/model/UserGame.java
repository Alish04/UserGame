package com.company.akeninbaev.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "User_Game")
public class UserGame implements Model {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Game game;

    public UserGame() {
    }

    public UserGame(int id, User user, Game game) {
        this.id = id;
        this.user = user;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userID";
    public static final String FIELD_GAME_ID = "gameID";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGame user_game = (UserGame) o;
        return Objects.equals(user, user_game.user) &&
                Objects.equals(game, user_game.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, game);
    }

    @Override
    public String toString() {
        return "User_Game{" +
                "user=" + user +
                ", game=" + game +
                '}';
    }
}
