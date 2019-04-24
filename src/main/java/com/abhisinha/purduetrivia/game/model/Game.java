package com.abhisinha.purduetrivia.game.model;

import com.abhisinha.purduetrivia.ignite.GameData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * One instance of a Trivia Game.
 */
@Data @NoArgsConstructor
public class Game {

    /*
    - current User
    - current Question
    - Number of points earned
    - number of questions answered
     */

    private static Random rand = new Random(System.currentTimeMillis());

    private final static int NUM_ROUNDS = 7;

    private static final long MAX_TIME_MIILLIS = 10_000;

    private User user;

    private Question question;

    private int points = 0;

    /**
     * Number of questions answered correctly.
     */
    private int correct = 0;

    /**
     * Total time taken for right answers.
     */
    private long totalTime = 0;

    private Set<Long> quesUsed;

    /**
     * Indicates whether the game has ended.
     */
    private boolean gameOver = false;

    public Game(User user) {
        this.user = user;
        quesUsed = new HashSet<>();

        nextQuestion();
    }

    private void nextQuestion() {
        long id = GameData.getRandomQuestionId();
        while (quesUsed.contains(id)) {
            id = GameData.getRandomQuestionId();
        }

        quesUsed.add(id);
        question = GameData.getQuestionById(id);
    }

    /**
     * Updates user point total for current game.
     *
     * @param time  Time taken to answer question
     */
    private void updatePoints(long time) {
        int earned = (int) (MAX_TIME_MIILLIS - time) / 100;

        points += (earned > 0) ? earned : 0;
    }

    private void cleanup() {
        int prevRA = (int) (user.getGames() * NUM_ROUNDS * user.getRatio());

        user.addGames(1);
        user.addTrophies(points);
        user.setRatio((double) (correct + prevRA) / (user.getGames() * NUM_ROUNDS));

        user.setRespTime(((user.getRespTime() * prevRA) + totalTime) / (correct + prevRA));

        GameData.updateUser(user);
    }

    /**
     *
     * @param opt
     * @param time
     * @return
     */
    public boolean checkAnswerAndUpdateGame(String opt, long time) {
        boolean res;
        if (res = question.getCorrectAnswer().equals(opt)) {
            updatePoints(time);
            correct++;
            totalTime += time;
        }

        if (quesUsed.size() == NUM_ROUNDS) {
            gameOver = true;
            cleanup();
        } else {
            nextQuestion();
        }

        return res;
    }
}
