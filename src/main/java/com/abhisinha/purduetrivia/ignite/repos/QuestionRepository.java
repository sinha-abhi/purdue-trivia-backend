package com.abhisinha.purduetrivia.ignite.repos;

import com.abhisinha.purduetrivia.game.models.Question;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "QuestionCache")
public interface QuestionRepository extends IgniteRepository<Question, Long> {
    Question getQuestionById(Long id);
}
