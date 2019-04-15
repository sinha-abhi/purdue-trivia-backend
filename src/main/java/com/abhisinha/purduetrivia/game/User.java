package com.abhisinha.purduetrivia.game;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Trivia Game User.
 *
 * @author Abhi Sinha
 * @version 1.0
 */
public class User {
    private static final AtomicLong ID_GEN = new AtomicLong();

    /**
     * Unique user id (index in Ignite).
     */
    @QuerySqlField(index = true)
    private Long id;

    /**
     * User name (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private String name;

    /**
     * User password (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private String password;

    /**
     * Do nothing - needed for Ignite binary deserialization
     */
    public User() {
        // do nothing
    }

    public User(String name, String password) {
        id = ID_GEN.incrementAndGet();

        this.name = name;
        this.password = password;
    }

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String password() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
