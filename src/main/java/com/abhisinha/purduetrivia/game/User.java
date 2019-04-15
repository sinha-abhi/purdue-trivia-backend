package com.abhisinha.purduetrivia.game;

import lombok.NoArgsConstructor;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;

/**
 * Trivia Game User.
 *
 * @author Abhi Sinha
 */
@Data @NoArgsConstructor
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
     * User trophies (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private Integer trophies = 0;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", trophies=" + trophies +
                '}';
    }
}
