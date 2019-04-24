package com.abhisinha.purduetrivia.game.controller;

import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.web.bind.annotation.*;

/**
 * Demo controller to test mappings.
 */
@RestController
public class UserController {

    @RequestMapping("/user/validate")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean verifyUser(@RequestParam(value="name", defaultValue = "") String name, @RequestParam(value="password", defaultValue = "") String password) {
        if ((name.length() == 0) || (password.length() == 0)) {
            return false;
        }

        return GameData.verifyUser(name, password);
    }

    @RequestMapping("/user/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean createUser(@RequestParam(value="name", defaultValue = "") String name, @RequestParam(value="password", defaultValue = "") String password) {
        if ((name.length() == 0) || (password.length() == 0)) {
            return false;
        }

        return GameData.createUser(name, password);
    }
}
