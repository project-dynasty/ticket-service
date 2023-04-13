package com.projectdynasty.ticket;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projectdynasty.ticket.config.JsonConfig;
import com.projectdynasty.ticket.security.JwtUtils;
import com.projectdynasty.ticket.security.RequestDetails;
import de.alexanderwodarz.code.JavaCore;
import de.alexanderwodarz.code.database.Database;
import de.alexanderwodarz.code.model.varible.VaribleMap;
import de.alexanderwodarz.code.web.WebCore;
import de.alexanderwodarz.code.web.rest.annotation.RestApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@RestApplication
public class TicketService {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final JsonConfig CONFIG = new JsonConfig(new File("config.json"));
    public static Database DATABASE;
    public static Database USER_DATABASE;
    public static JwtUtils JWT_UTILS;
    public static JWTVerifier VERIFIER;

    public static void main(String[] args) throws Exception {
        initSettings();
        VERIFIER = JWT.require(Algorithm.HMAC256(CONFIG.get("jwt", Jwt.class).getKey())).withIssuer(CONFIG.get("jwt", Jwt.class).getIss()).build();
        JWT_UTILS = new JwtUtils();
        DatabaseConfig databaseConfig = CONFIG.get("db", DatabaseConfig.class);
        DATABASE = new Database(databaseConfig.getHost(), databaseConfig.getUsername(), databaseConfig.getPassword(), databaseConfig.getDb());
        USER_DATABASE = new Database(databaseConfig.getHost(), databaseConfig.getUsername(), databaseConfig.getPassword(), "users");
        VaribleMap map = new VaribleMap();
        map.put("port", "3841");
        WebCore.start(TicketService.class, map);
    }

    private static void initSettings() {
        if (new File("config.json").exists())
            return;
        Jwt jwt = new Jwt("TCP Rest API", JavaCore.getRandomString(128));
        DatabaseConfig database = new DatabaseConfig("secret", "localhost", "tcp_data", "restapi", 3306);
        CONFIG.set("jwt", jwt);
        CONFIG.set("db", database);
        CONFIG.saveConfig();
        System.exit(1);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DatabaseConfig {
        private String password, host, db, username;
        private int port;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Jwt {
        private String iss, key;
    }

}
