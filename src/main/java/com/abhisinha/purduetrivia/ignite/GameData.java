package com.abhisinha.purduetrivia.ignite;

import com.abhisinha.purduetrivia.game.model.Question;
import com.abhisinha.purduetrivia.game.model.User;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameData {
    /**
     * Ignite instance.
     */
    private static Ignite ignite;

    /**
     * User unique id generator.
     */
    private static IgniteAtomicSequence userIdGen;

    /**
     * Question unique id generator.
     */
    private static IgniteAtomicSequence questionIdGen;

    /**
     * User cache stored in the Ignite cluster.
     */
    private static IgniteCache<Long, User> userCache;

    /**
     * Question cache stored in the Ignite cluster.
     */
    private static IgniteCache<Long, Question> questionCache;

    /**
     * User key set.
     */
    private static Set<Long> USER_KEY_SET;

    /**
     * Question key set.
     */
    private static Set<Long> QUESTION_KEY_SET;

    static {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/ignite/ignite-bean.xml");
        ignite = (Ignite) context.getBean("myIgniteSpringBean");

        if (ignite.cluster().active()) {
            System.out.println("[GameData] Ignite successfully started!");
        } else {
            System.err.println("[GameData] Ignite not active!");
        }

        userIdGen = ignite.atomicSequence("userSeq", 0, true);
        questionIdGen = ignite.atomicSequence("questionSeq", 0, true);

        Collection<String> caches = ignite.cacheNames();
        System.out.println("[GameData] Available caches: " + caches);
        if (caches.contains("UserCache")) {
            userCache = ignite.cache("UserCache");
        } else {
            System.err.println("[GameData] Cannot find UserCache! Terminating...");
            System.exit(-1);
        }

        if (caches.contains("QuestionCache")) {
            questionCache = ignite.cache("QuestionCache");
        } else {
            System.err.println("[GameData] Cannot find QuestionCache! Terminating...");
            System.exit(-1);
        }

        USER_KEY_SET = new HashSet<>();
        QUESTION_KEY_SET = new HashSet<>();
    }

    /**
     * Get all users from Ignite Cache with the given name.
     *
     * @param name User name
     * @return <tt>List</tt> of users with given name
     */
    public static List<User> getUserByName(String name) {
        List<User> users = new ArrayList<>();

        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select * from User where name like ?")
                        .setArgs(name));

        for (List res : cursor.getAll()) {
            users.add(new User(Long.parseLong(res.get(0).toString()),
                    res.get(1).toString(),
                    res.get(2).toString(),
                    Integer.parseInt(res.get(3).toString()),
                    Double.parseDouble(res.get(4).toString()),
                    Double.parseDouble(res.get(5).toString()),
                    Integer.parseInt(res.get(6).toString()))
            );
        }

        return users;
    }

    /**
     * Get user from Ignite Cache with the given unique id.
     *
     * @param id Unique user id
     * @return <tt>User</tt> with given id
     */
    public static User getUserById(long id) {
        return userCache.get(id);
    }

    /**
     * Gets all users from Ignite Cache.
     *
     * @return <tt>Map</tt> of users to their id.
     */
    public static Map<Long, User> getAllUsers() {
        return userCache.getAll(USER_KEY_SET);
    }

    /**
     * Gets all user names from Ignite Cache.
     *
     * @return  {@code List} of all usernames
     */
    public static List<String> getAllUsernames() {
        List<String> names = new ArrayList<>();

        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select name from User"));

        for (List<?> res : cursor.getAll()) {
            names.add(res.get(0).toString());
        }

        return names;
    }

    /**
     * Ranks users based on number of trophies and gives a list of highest ranked users, whose size
     * is given by either {@code places}, or if there are fewer users than places, the number of users
     * plus empty users.
     *
     * @param places Number of users in leaderboard
     * @return <tt>List</tt> of users ranked from first to last
     */
    public static List<User> getTrophyLeaderboard(int places) {
        List<User> users = new ArrayList<>();

        int ranks = (places > USER_KEY_SET.size()) ? USER_KEY_SET.size() : places;

        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select top ? * from User order by trophies desc")
                        .setArgs(ranks));

        for (List<?> res : cursor.getAll()) {
            users.add(new User(Long.parseLong(res.get(0).toString()),
                    res.get(1).toString(),
                    res.get(2).toString(),
                    Integer.parseInt(res.get(3).toString()),
                    Double.parseDouble(res.get(4).toString()),
                    Double.parseDouble(res.get(5).toString()),
                    Integer.parseInt(res.get(6).toString()))
            );
        }

        for (int i = users.size(); i < places; i++) {
            users.add(new User(-1, "", ""));
        }

        return users;
    }

    public static List<User> getRatioLeaderboard(int places) {
List<User> users = new ArrayList<>();

        int ranks = (places > USER_KEY_SET.size()) ? USER_KEY_SET.size() : places;

        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select top ? * from User order by ratio desc")
                        .setArgs(ranks));

        for (List<?> res : cursor.getAll()) {
            users.add(new User(Long.parseLong(res.get(0).toString()),
                    res.get(1).toString(),
                    res.get(2).toString(),
                    Integer.parseInt(res.get(3).toString()),
                    Double.parseDouble(res.get(4).toString()),
                    Double.parseDouble(res.get(5).toString()),
                    Integer.parseInt(res.get(6).toString()))
            );
        }

        for (int i = users.size(); i < places; i++) {
            users.add(new User(-1, "", ""));
        }

        return users;
    }

    public static List<User> getRespTimeLeaderboard(int places) {
        List<User> users = new ArrayList<>();

        int ranks = (places > USER_KEY_SET.size()) ? USER_KEY_SET.size() : places;

        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select top ? * from User order by respTime asc")
                        .setArgs(ranks));

        for (List<?> res : cursor.getAll()) {
            users.add(new User(Long.parseLong(res.get(0).toString()),
                    res.get(1).toString(),
                    res.get(2).toString(),
                    Integer.parseInt(res.get(3).toString()),
                    Double.parseDouble(res.get(4).toString()),
                    Double.parseDouble(res.get(5).toString()),
                    Integer.parseInt(res.get(6).toString()))
            );
        }

        for (int i = users.size(); i < places; i++) {
            users.add(new User(-1, "", ""));
        }

        return users;
    }

    /**
     * Forces a new user to be added to the Ignite Cache.
     *
     * @param name     User name
     * @param password User password
     */
    private static void addUser(String name, String password) {
        try (IgniteDataStreamer<Long, User> userStreamer = ignite.dataStreamer("UserCache")) {
            userStreamer.allowOverwrite(false);

            USER_KEY_SET.add(userIdGen.incrementAndGet());
            userStreamer.addData(userIdGen.get(), new User(userIdGen.get(), name, password));
        }
    }

    /**
     * Creates a new user and adds it to the Ignite Cache, after verifying that the username is unique.
     *
     * @param name  User name
     * @param password  User password
     * @return {@code true} if the user was created, {@code false} otherwise
     */
    public static boolean createUser(String name, String password) {
        if (getAllUsernames().contains(name)) {
            return false;
        } else {
            addUser(name, password);
            return true;
        }
    }

    /**
     * Verifies if there exists a user with the given name and password.
     *
     * @param name     User name
     * @param password User password
     * @return {@code true} if name and password combination exists, {@code false} otherwise
     */
    public static boolean verifyUser(String name, String password) {
        QueryCursor<List<?>> cursor = userCache.query(
                new SqlFieldsQuery("select * from User where name like ? and password like ?")
                        .setArgs(name, password));

        return !cursor.getAll().isEmpty();
    }

    /**
     * Updates user in cache given by its POJO.
     *
     * @param usr User to be updated
     */
    public static void updateUser(User usr) {
        userCache.query(
                new SqlFieldsQuery("update User set games = ?, " +
                        "respTime = ?, " +
                        "ratio = ?, " +
                        "trophies = ? " +
                        "where id = ?")
                        .setArgs(usr.getGames(), usr.getRespTime(), usr.getRatio(), usr.getTrophies(), usr.getId()));
    }

    /**
     * Get all question ids.
     *
     * @return <tt>Set</tt> of all ids
     */
    public static Set<Long> getAllQuestionIds() {
        return QUESTION_KEY_SET;
    }

    /**
     * Creates a new <tt>Question</tt>.
     *
     * @param question Question statement
     * @param opt      Options for this questions
     *                 The first option is assumed to be correct on question creation.
     *                 Note that only four options are supported by the game, even though
     *                 this implementation supports more.
     */
    public static void addQuestion(String question, String... opt) {
        List<Pair<Boolean, String>> options = new ArrayList<>();

        options.add(new ImmutablePair<>(true, opt[0]));
        for (int i = 1; i < opt.length; i++) {
            options.add(new ImmutablePair<>(false, opt[i]));
        }

        try (IgniteDataStreamer<Long, Question> questionStreamer = ignite.dataStreamer("QuestionCache")) {
            questionStreamer.allowOverwrite(false);

            QUESTION_KEY_SET.add(questionIdGen.incrementAndGet());
            questionStreamer.addData(questionIdGen.get(), new Question(questionIdGen.get(), question, options));
        }
    }

    /**
     * Get <tt>Question</tt> from Ignite Cache with the given unique id.
     *
     * @param id Unique question id
     * @return <tt>Question</tt> with id
     */
    public static Question getQuestionById(long id) {
        return questionCache.get(id);
    }

    /**
     * Get a random <tt>Question</tt> from the Ignite Cache.
     *
     * @return Random <tt>Question</tt>
     */
    public static Long getRandomQuestionId() {
        int id = ThreadLocalRandom.current().nextInt(QUESTION_KEY_SET.size());

        return new ArrayList<>(QUESTION_KEY_SET).get(id);
    }
}
