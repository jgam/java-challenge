package jp.co.axa.apidemo.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCacheConfiguration {

    final String CACHE_NAME = "employees";
    final String INSTANCE_NAME = "employee-hazelcast-instance";

    @Bean
    public com.hazelcast.config.Config getHazelcastConfig() {

        return new Config().setInstanceName(INSTANCE_NAME)
                .addMapConfig(
                        new MapConfig()
                                .setName(CACHE_NAME)
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(60)
                                .setMaxIdleSeconds(40));
    }
}
