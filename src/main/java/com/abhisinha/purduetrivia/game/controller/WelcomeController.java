package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.game.model.User;
import com.abhisinha.purduetrivia.game.model.Welcome;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public Welcome getGenericWelcome() {
        return new Welcome();
    }

    @GetMapping("/user/{username}")
    public Welcome getUserWelcome(@PathVariable String username) {
        return new Welcome(username);
    }


    @RequestMapping("/leaderboard")
    public List<User> getTrophyLeaderboard() {
        return GameData.getTrophyLeaderboard(5);
    }
}
