package com.abhisinha.purduetrivia.game;

import com.abhisinha.purduetrivia.ignite.config.IgniteCacheCfg;
import com.abhisinha.purduetrivia.ignite.repos.QuestionRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QuestionData {
    /**
     * Question database.
     */
    private static QuestionRepository questionRepo;

    /**
     * Unique sequence generator.
     */
    private static IgniteAtomicSequence idGen;

    static {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(IgniteCacheCfg.class);
        context.refresh();

        questionRepo = context.getBean(QuestionRepository.class);
        Ignite ignite = context.getBean(Ignite.class);

        if (ignite.cluster().active()) {
             idGen = ignite.atomicSequence("userId", 0, true);
        } else {
            System.err.println("[QuestionData] Ignite cluster not active! Check server state...");
        }
    }

    /**
     * Creates a new <tt>Question</tt>.
     *
     * @param question  Question statement
     * @param opt Options question
     *              The first option is assumed to be correct on question creation.
     *              Only four options are supported.
     */
    public static void addQuestion(String question, String... opt) {
        // TODO
    }
}
