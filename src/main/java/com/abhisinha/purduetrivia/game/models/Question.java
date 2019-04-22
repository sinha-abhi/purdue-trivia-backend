package com.abhisinha.purduetrivia.game.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.List;

@Data @NoArgsConstructor
public class Question {

    @QuerySqlField(index = true)
    private Long id;

    /**
     * Question statement.
     */
    private String question;

    /**
     * Question options.
     */
    List<Pair<Boolean, String>> options;

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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
