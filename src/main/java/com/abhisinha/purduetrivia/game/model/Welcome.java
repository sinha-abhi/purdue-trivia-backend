package com.abhisinha.purduetrivia.game.model;

public class Welcome {
    private String message = "Welcome to Purdue Ultimate Trivia";

    public Welcome() {
        this.message += "!";
    }

    public Welcome(String name) {
        this.message += ", " + name + "!";
    }

    public String getMessage() {
        return message;
    }
}
