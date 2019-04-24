package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.game.model.Game;
import com.abhisinha.purduetrivia.game.model.Question;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private static Game game;

    @RequestMapping("/game")
    public Game startGame(@RequestParam(value = "name", defaultValue = "") String name) {
        System.out.println("******* REACHED HERE ************");
        game = new Game(GameData.getUserByName(name).get(0));
        System.out.println("******* REACHED HERE ************");
        return game;
    }

    /**
     * Checks if the user's choice is the correct response to the current question.
     *
     * @param opt Option chosen by the user
     * @return {@code true} if answer is correct, {@code false} otherwise
     */
    @RequestMapping("/game/option")
    public boolean verifyUserChoice(@RequestParam(value = "opt", defaultValue = "") String opt,
                                    @RequestParam(value = "time", defaultValue = "0.0") long time) {
        if (opt.length() == 0) {
            return false;
        }

        return game.checkAnswerAndUpdateGame(opt, time);
    }

    @RequestMapping("/game/next")
    public Game nextGameInstance() {
        return game;
    }
}
