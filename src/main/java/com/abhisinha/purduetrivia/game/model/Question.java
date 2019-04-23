package com.abhisinha.purduetrivia.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trivia Game Question.
 *
 * @author Abhi Sinha
 */
@Data @NoArgsConstructor
public class Question {
    /**
     * Unique question id (index in Ignite).
     */
    @QuerySqlField(index = true)
    private Long id;

    /**
     * Question statement.
     */
    private String question;

    /**
     * Question options.
     */
    private List<Pair<Boolean, String>> options;

    public Question(Long id, String question, List<Pair<Boolean, String>> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<Pair<Boolean, String>> getOptions() {
        return options;
    }

    public List<String> getShuffledOptions() {
        List<String> opts = new ArrayList<>();

        for (Pair<Boolean, String> p : options) {
            opts.add(p.getRight());
        }

        Collections.shuffle(opts);

        return opts;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
