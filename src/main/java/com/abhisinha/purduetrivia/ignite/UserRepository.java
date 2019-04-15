package com.abhisinha.purduetrivia.ignite;

import com.abhisinha.purduetrivia.game.User;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "UserCache")
public interface UserRepository extends IgniteRepository<User, Long> {
    // TODO: populate with queries needed for game
}
