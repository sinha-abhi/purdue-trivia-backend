package com.abhisinha.purduetrivia.ignite;

import com.abhisinha.purduetrivia.game.User;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.Query;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;

import java.util.List;

@RepositoryConfig(cacheName = "UserCache")
public interface UserRepository extends IgniteRepository<User, Long> {
    // TODO: populate with queries needed for game

    /**
     * Gets all users with the given id.
     *
     * @param id User id.
     * @return User with given id.
     */
    User getUserById(Long id);

    /**
     * Gets all the users with the given name.
     *
     * @param name User name.
     * @return A list of Users with the given name.
     */
    List<User> getUserByName(String name);

}
