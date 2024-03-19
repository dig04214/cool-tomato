package com.wp.user.global.config;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@RequiredArgsConstructor
@EnableRedisRepositories
@Configuration
public class RedisConfig {

    private final RedisConfiguration redisConfiguration;
    @Value("${redis.password}")
    private String PASSWORD;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        RedisStaticMasterReplicaConfiguration staticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(
                redisConfiguration.getMaster().getHost(),
                redisConfiguration.getMaster().getPort()
        );
        redisConfiguration.getNodes().forEach(slave -> staticMasterReplicaConfiguration.addNode(slave.getHost(), slave.getPort()));
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);
        lettuceConnectionFactory.setPassword(PASSWORD);
        return lettuceConnectionFactory;
    }
}