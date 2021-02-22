package com.company.akeninbaev;

import com.company.akeninbaev.configuration.DataBaseConfiguration;
import com.company.akeninbaev.configuration.JdbcDataBaseConfiguration;
import com.company.akeninbaev.controller.*;
import com.company.akeninbaev.deserializers.GameDeserializer;
import com.company.akeninbaev.deserializers.UserDeserializer;
import com.company.akeninbaev.model.Game;
import com.company.akeninbaev.model.User;
import com.company.akeninbaev.model.UserRole;
import com.company.akeninbaev.model.UserGame;
import com.company.akeninbaev.serializers.GameSerializer;
import com.company.akeninbaev.serializers.UserSerializer;
import com.company.akeninbaev.service.GameService;
import com.company.akeninbaev.service.Service;
import com.company.akeninbaev.service.UserGameService;
import com.company.akeninbaev.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import static com.company.akeninbaev.model.UserRole.*;
import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        DataBaseConfiguration configuration = new JdbcDataBaseConfiguration("jdbc:sqlite:C:\\Users\\PREDATOR\\OneDrive\\Рабочий стол\\OOP\\UserAndGame.db");

        Dao<User, Integer> userDao = DaoManager.createDao(configuration.connectionSource(), User.class);
        Dao<Game, Integer> gameDao = DaoManager.createDao(configuration.connectionSource(), Game.class);
        Dao<UserGame, Integer> libraryDao = DaoManager.createDao(configuration.connectionSource(), UserGame.class);

        Service<User> userService = new UserService(userDao);
        Service<Game> gameService = new GameService(gameDao);
        Service<UserGame> userGameService = new UserGameService(libraryDao);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new UserSerializer());
        simpleModule.addSerializer(new GameSerializer());
        simpleModule.addDeserializer(User.class, new UserDeserializer());
        simpleModule.addDeserializer(Game.class, new GameDeserializer());

        ObjectMapper objectMapper = new ObjectMapper().registerModule(simpleModule);

        Controller<User> userController = new UserController(userService, objectMapper);
        Controller<Game> gameController = new GameController(gameService, objectMapper);
        Controller<UserGame> userGameController = new UserGameController(userGameService,objectMapper);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.prefer405over404 = true;
            javalinConfig.enableCorsForAllOrigins();
            javalinConfig.accessManager((handler, ctx, permittedRoles) -> {
                UserRole userRole = getUserRole(ctx, userDao);
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx);
                } else {
                    ctx.status(401).result("Unauthorized");
                    LOGGER.info(String.valueOf(userRole));
                }
            });

        });
        app.routes(() -> {
            path("users", () -> {
                get(userController::getAll, roles(ADMIN, COMMON));
                post(userController::post, roles(ADMIN, COMMON, ANONYMOUS));

                path(":id", () -> {
                    get(ctx -> userController.getOne(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN, COMMON));
                    patch(ctx -> userController.patch(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN, COMMON));
                    delete(ctx -> userController.delete(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN, COMMON));
                });
            });

            path("games", () -> {
                get(gameController::getAll, roles(ADMIN));
                post(gameController::post, roles(ADMIN, COMMON, ANONYMOUS));

                path(":id", () -> {
                    get(ctx -> gameController.getOne(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN, COMMON));
                    patch(ctx -> gameController.patch(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN));
                    delete(ctx -> gameController.delete(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN));
                });
            });

            path("owngames", () -> {
                get(userGameController::getAll, roles(ADMIN, COMMON));
                post(userGameController::post, roles(ADMIN, COMMON));

                path(":id", () -> {
                    get(ctx -> userGameController.getOne(ctx, ctx.pathParam("id", Integer.class).get()), roles(ADMIN, COMMON));
                });
            });
        });
        app.start(8080);
    }

    public static UserRole getUserRole(Context context, Dao<User, Integer> dao){
        UserRole role = ANONYMOUS;
        if (context.basicAuthCredentialsExist()) {
            String username = context.basicAuthCredentials().getUsername();
            String password = context.basicAuthCredentials().getPassword();
            try {
                List<User> users = dao.queryForAll();
                for (int i = 0; i < users.size(); i++) {
                    LOGGER.info(String.valueOf(users.get(i).getUserRole()));
                    if (users.get(i).getNickname().equals(username) && BCrypt.checkpw(password, users.get(i).getPassword())) {
                        role = users.get(i).getUserRole();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return role;
    }
}
