package com.abhisinha.purduetrivia.game;

import com.abhisinha.purduetrivia.ignite.UserCacheCfg;
import com.abhisinha.purduetrivia.ignite.UserRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class UserDataTest {

    /**
     * User database.
     */
    private static UserRepository userRepo;

    /**
     * Unique sequence generator.
     */
    private static IgniteAtomicSequence idGen;

    static {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserCacheCfg.class);
        context.refresh();

        userRepo = context.getBean(UserRepository.class);
        Ignite ignite = context.getBean(Ignite.class);

        if (ignite.cluster().active()) {
             idGen = ignite.atomicSequence("userId", 0, true);
        } else {
            System.err.println("[UserDataTest] Ignite cluster not active! Check server state...");
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Iterable<User> iterable = userRepo.findAll();
        iterable.forEach(users::add);

        return users;
    }

    public static void addUser(String name, String password) {
        User user = new User(idGen.getAndIncrement(), name, password);
        userRepo.save(idGen.get(), user);
    }

    public static User getUserById(long id) {
        return userRepo.getUserById(id);
    }

    public static List<User> getUserbyName(String name) {
        List<User> users = userRepo.getUserByName(name);

        System.out.println("[UserDataTest] num of users with name " + name + ": " + users.size());

        return users;
    }

    /**
     * For testing purposes.
     */
    private static void populateRepo() {

        for (int i = 0; i < 100; i++) {
            addUser("user-" + i, "password-" + i);

            if (i % 10 == 0) {
                System.out.println("[UserDataTest] Created: " + i);
            }
        }
    }

    /**
     * For testing purposes.
     */
    public static void main(String... args) {
        System.out.println("[UserDataTest] num of users in repo start: " + userRepo.count());

        populateRepo();

        List<User> namedUsers = getUserbyName("user-10");
        for (User u : namedUsers) {
            System.out.println(u);
        }

        List<User> users = getAllUsers();
        System.out.println("[UserDataTest] num of  users in repo at end: " + users.size());
        if (userRepo.count() != users.size()) {
            System.out.println("[UserDataTest] wtf???");
        }

    }
}