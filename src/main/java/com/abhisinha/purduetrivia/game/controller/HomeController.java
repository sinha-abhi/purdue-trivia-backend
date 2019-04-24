package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.game.model.User;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HomeController implements ErrorController {

    @RequestMapping("/leaderboard/trophies")
    public List<User> getTrophyLeaderboard(@RequestParam(value = "name", defaultValue = "") String name) {
        List<User> users = GameData.getUserByName(name);

        users.addAll(GameData.getTrophyLeaderboard(5));
        System.out.println("%%%%% size: " + users.size());

        return users;
    }

    @RequestMapping("/leaderboard/ratio")
    public List<User> getRatioLeaderboard(@RequestParam(value = "name", defaultValue = "") String name) {
        List<User> users = GameData.getUserByName(name);

        users.addAll(GameData.getRatioLeaderboard(5));

        return users;
    }

    @RequestMapping("/leaderboard/respTime")
    public List<User> getRespTimeLeaderboard(@RequestParam(value = "name", defaultValue = "") String name) {
        List<User> users = GameData.getUserByName(name);

        users.addAll(GameData.getRespTimeLeaderboard(5));

        return users;
    }

    @RequestMapping("/error")
    public String onError(HttpServletRequest req) {
        Object stat = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (stat != null) {
            Integer code = Integer.valueOf(stat.toString());

            if (code == HttpStatus.NOT_FOUND.value()) {
                return "404";
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
