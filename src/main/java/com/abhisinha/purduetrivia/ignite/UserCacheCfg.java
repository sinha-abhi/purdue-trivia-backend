package com.abhisinha.purduetrivia.ignite;

import com.abhisinha.purduetrivia.game.User;
import com.sun.org.apache.bcel.internal.generic.POP;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableIgniteRepositories
public class UserCacheCfg {

    /**
     * User cache name.
     */
    private static final String USER_CACHE = "UserCache";

    /**
     * Location of config for Ignite clusters.
     */
    private static String CONFIG_XML;

    static {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String file = loader.getResource("config/ignite/user-persistent-storage.xml").getFile();
        if (file != null) {
            CONFIG_XML = file;
        } else {
            throw new NullPointerException("No User Persistent Storage XML File");
        }
    }

    /**
     * Create Ignite instance bean.
     */
    @Bean
    public Ignite igniteInstance() {
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(CONFIG_XML)) {
            ignite.cluster().active(true);

            CacheConfiguration<Long, User> cacheCfg = new CacheConfiguration<>(USER_CACHE);

            cacheCfg.setBackups(1);
            cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
            cacheCfg.setIndexedTypes(Long.class, User.class);

            return ignite;
        }
    }
}
