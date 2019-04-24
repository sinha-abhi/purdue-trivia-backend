package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.game.model.User;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.web.bind.annotation.*;

/**
 * Demo controller to test mappings.
 */
@RestController
public class UserController {

    @RequestMapping("/user/validate")
    public boolean verifyUser(@RequestParam(value="name", defaultValue = "") String name, @RequestParam(value="password", defaultValue = "") String password) {
        if ((name.length() == 0) || (password.length() == 0)) {
            return false;
        }

        return GameData.verifyUser(name, password);
    }

    @RequestMapping("/user/create")
    public boolean createUser(@RequestParam(value="name", defaultValue = "") String name, @RequestParam(value="password", defaultValue = "") String password) {
        if ((name.length() == 0) || (password.length() == 0)) {
            return false;
        }

        return GameData.createUser(name, password);
    }
}
