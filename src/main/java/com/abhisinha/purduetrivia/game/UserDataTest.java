package com.abhisinha.purduetrivia.game;

import com.abhisinha.purduetrivia.ignite.UserCacheCfg;
import com.abhisinha.purduetrivia.ignite.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.TreeMap;

public class UserDataTest {
    private static AnnotationConfigApplicationContext context;

    private static UserRepository userRepo;

    private static void igniteUserDataInit() {
        context = new AnnotationConfigApplicationContext();
        context.register(UserCacheCfg.class);
        context.refresh();

        userRepo = context.getBean(UserRepository.class);
    }

    /**
     * For testing purposes.
     */
    private static void populateRepo() {
        Map<Long, User> users = new TreeMap<>();

        for (long l = 0; l < 100; l++) {
            users.put(l, new User(l, "user-" + l, "password-" + l));

            if (l % 10 == 0) {
                System.out.println("*** Created: " + l);
            }
        }

        userRepo.save(users);

        System.out.println("*** Added " + userRepo.count() + " users to the repository");
    }

    /**
     * For testing purposes.
     */
    public static void main(String... args) {
        igniteUserDataInit();
        populateRepo();
    }
}
