package com.abhisinha.purduetrivia;

import com.abhisinha.purduetrivia.game.init.QuestionLoader;
import com.abhisinha.purduetrivia.game.model.User;
import com.abhisinha.purduetrivia.ignite.GameData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;
import java.util.Random;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@CrossOrigin(origins = "http://localhost:4200")
public class TriviaApplication {

    public static void main(String... args) {
        SpringApplication.run(TriviaApplication.class, args);
        QuestionLoader.loadQuestions();


        System.out.println("TESTING CACHES...");

        for (int i = 0; i < 10; i++) {
            System.out.println(GameData.createUser("user-" + i, "pass-" + i));

        }
//
////        Map<Long, User> users = GameData.getAllUsers();
////        int i = 1;
////        Random rand = new Random();
////        for (Map.Entry<Long, User> u : users.entrySet()) {
////            u.getValue().addTrophies(i);
////            GameData.updateUser(u.getValue());
////
////            i += rand.nextInt();
////        }
////
////        System.out.println("%%%% leaderboard: " + GameData.getTrophyLeaderboard(5));
//
//        System.out.println("usernames: " + GameData.getAllUsernames());
    }

}
