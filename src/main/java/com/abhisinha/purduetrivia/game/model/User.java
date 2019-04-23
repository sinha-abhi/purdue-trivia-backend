package com.abhisinha.purduetrivia.game.model;

import lombok.NoArgsConstructor;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import lombok.Data;

/**
 * Trivia Game User.
 *
 * @author Abhi Sinha
 */
@Data @NoArgsConstructor
public class User {
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

    public User(Long id, String name, String password, Integer games, Double respTime, Double ratio, Integer trophies) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.games = games;
        this.respTime = respTime;
        this.ratio = ratio;
        this.trophies = trophies;
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

    public void addGames(int games) {
        this.games += games;
    }

    public void addTrophies(int trophies) {
        this.trophies += trophies;
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
