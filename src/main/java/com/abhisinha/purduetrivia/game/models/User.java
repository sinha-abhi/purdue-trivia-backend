package com.abhisinha.purduetrivia.game.models;

import lombok.NoArgsConstructor;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Trivia Game User.
 *
 * @author Abhi Sinha
 */
@Entity
@Data @NoArgsConstructor
public class User {
    /**
     * Unique user id (index in Ignite).
     */
    @Id
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
     * Number of games played (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private Integer games = 0;

    /**
     * Average response time for correct answers (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private Double respTime = 0.0;

    /**
     * Ratio of correct answers to total questions attempted (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private Double ratio = 0.0;

    /**
     * User trophies (indexed in Ignite).
     */
    @QuerySqlField(index = true)
    private Integer trophies = 0;

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getGames() {
        return games;
    }

    public Double getRespTime() {
        return respTime;
    }

    public Double getRatio() {
        return ratio;
    }

    public Integer getTrophies() {
        return trophies;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", games=" + games +
                ", respTime=" + respTime +
                ", ratio=" + ratio +
                ", trophies=" + trophies +
                '}';
    }

}
