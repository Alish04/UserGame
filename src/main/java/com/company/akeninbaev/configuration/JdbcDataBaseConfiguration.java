package com.company.akeninbaev.configuration;

import com.company.akeninbaev.exception.ApplicationException;
import com.company.akeninbaev.model.Game;
import com.company.akeninbaev.model.User;
import com.company.akeninbaev.model.UserGame;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class JdbcDataBaseConfiguration implements DataBaseConfiguration{
    private final ConnectionSource connectionSource;

    public JdbcDataBaseConfiguration(String jdbcConnectionString) {
        try {
            connectionSource = new JdbcConnectionSource(jdbcConnectionString);

            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Game.class);
            TableUtils.createTableIfNotExists(connectionSource, UserGame.class);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new ApplicationException("Can't initialize database connection", throwable);
        }
    }

    @Override
    public ConnectionSource connectionSource() {
        return connectionSource;
    }
}
