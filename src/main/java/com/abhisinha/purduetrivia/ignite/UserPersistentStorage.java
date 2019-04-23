// EXAMPLE FILE -- NOT NEEDED
package com.abhisinha.purduetrivia.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import com.abhisinha.purduetrivia.game.model.User;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class UserPersistentStorage {

    /**
     * User cache name.
     */
    private static final String USER_CACHE = "UserCache";

    /**
     * For testing purposes: populate cache with some data.
     */
    private static final boolean POPULATE = true;

    private static Set<Long> KEYS_SET;

    private static String CONFIG_XML;

    static {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String file = loader.getResource("config/ignite/user-persistent-storage.xml").getFile();
        if (file != null) {
            CONFIG_XML = file;
        } else {
            throw new NullPointerException("No User Persistent Storage XML File");
        }

        KEYS_SET = new HashSet<>();
        for (long i = 0; i < 1000; i++) {
            KEYS_SET.add(i);
        }
    }

    public static void main(String... args) {
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start(CONFIG_XML)) {
            ignite.cluster().active(true);

            CacheConfiguration<Long, User> cacheCfg = new CacheConfiguration<>(USER_CACHE);

            cacheCfg.setBackups(1);
            cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
            cacheCfg.setIndexedTypes(Long.class, User.class);

            IgniteCache<Long, User> cache = ignite.getOrCreateCache(cacheCfg);

            if (POPULATE) {
                System.out.println("[UserPersistentStorage] Populating the cache...");

                try (IgniteDataStreamer<Long, User> streamer = ignite.dataStreamer(USER_CACHE)) {
                    streamer.allowOverwrite(false);

                    for (long i = 0; i < 100; i++) {
                        streamer.addData(i, new User(i, "user-" + i, "password-" + i));

                        if (i % 10 == 0) {
                            System.out.println("[UserPersistentStorage] Added: " + i);
                        }
                    }
                }
            }

            Map<Long, User> entries = cache.getAll(KEYS_SET);

            if (entries.isEmpty()) {
                System.out.println("[UserPersistentStorage] CACHE IS EMPTY");
            } else {
                for (Map.Entry<Long, User> entry : entries.entrySet()) {
                    System.out.println("Entry [key=" + entry.getKey() + ", value=" + entry.getValue() + "]");
                }
            }

            // Method 2: Using get
            User usr = cache.get(45L);
            System.out.println("[UserPersistentStorage] GET Result: " + usr);

        }

    }
}
