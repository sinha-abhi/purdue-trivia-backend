package com.abhisinha.purduetrivia.ignite.config;

import com.abhisinha.purduetrivia.game.models.Question;
import com.abhisinha.purduetrivia.game.models.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata20.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableIgniteRepositories
@ComponentScan("com.abhisinha")
public class IgniteCacheCfg {

    /**
     * User cache name.
     */
    private static final String USER_CACHE = "UserCache";

    /**
     * Question cache name.
     */
    private static final String QUESTION_CACHE = "QuestionCache";

    /**
     * Create Ignite instance bean.
     */
    @Bean
    public Ignite igniteInstance() {
        Ignition.setClientMode(true);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("userDataNode");

        TcpDiscoverySpi discoSpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500", "127.0.0.1:47501", "127.0.0.1:47502"));
        discoSpi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(discoSpi);

        CacheConfiguration<Long, User> userCacheCfg = new CacheConfiguration<>(USER_CACHE);
        userCacheCfg.setGroupName("group1");
        userCacheCfg.setBackups(1);
        userCacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        userCacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        userCacheCfg.setIndexedTypes(Long.class, User.class);

        CacheConfiguration<Long, Question> quesCacheCfg = new CacheConfiguration<>(QUESTION_CACHE);
        quesCacheCfg.setGroupName("group1");
        quesCacheCfg.setBackups(1);
        quesCacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        quesCacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        quesCacheCfg.setIndexedTypes(Long.class, Question.class);

        cfg.setCacheConfiguration(userCacheCfg, quesCacheCfg);

        return Ignition.start(cfg);
    }
}
