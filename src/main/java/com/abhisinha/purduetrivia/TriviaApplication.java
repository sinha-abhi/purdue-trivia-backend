package com.abhisinha.purduetrivia;

import com.abhisinha.purduetrivia.game.init.QuestionLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@CrossOrigin(origins = "http://localhost:4200")
public class TriviaApplication {

    public static void main(String... args) {
        SpringApplication.run(TriviaApplication.class, args);
        QuestionLoader.loadQuestions();
    }

}
