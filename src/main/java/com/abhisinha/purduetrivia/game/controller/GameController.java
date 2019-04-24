package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.game.model.Game;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private static Game game;

    @RequestMapping("/game")
    @CrossOrigin(origins = "http://localhost:4200")
    public Game startGame(@RequestParam(value = "name", defaultValue = "") String name) {
        game = new Game(GameData.getUserByName(name).get(0));
        return game;
    }

    /**
     * Checks if the user's choice is the correct response to the current question.
     *
     * @param opt Option chosen by the user
     * @return {@code true} if answer is correct, {@code false} otherwise
     */
    @RequestMapping("/game/option")
    @CrossOrigin(origins = "http://localhost:4200")
    public Pair<Boolean, String> verifyUserChoice(@RequestParam(value = "opt", defaultValue = "") String opt,
                                                  @RequestParam(value = "time", defaultValue = "0.0") long time) {
        String correct = game.currentQuestion().getCorrectAnswer();
        if (opt.length() == 0) {
            return new ImmutablePair<>(false, correct);
        }

        return new ImmutablePair<>(game.checkAnswerAndUpdateGame(opt, time), correct);
    }

    @RequestMapping("/game/next")
    @CrossOrigin(origins = "http://localhost:4200")
    public Game nextGameInstance() {
        return game;
    }
}
