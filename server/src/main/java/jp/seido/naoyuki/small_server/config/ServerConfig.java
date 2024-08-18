package jp.seido.naoyuki.small_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jp.seido.naoyuki.lib.service.UserStore;
import jp.seido.naoyuki.lib.service.UserStoreDynamo;
import jp.seido.naoyuki.lib.service.UserStoreMemory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class ServerConfig {

    @Bean
    @Profile("memory-db")
    public UserStore userStoreInMemory() {
        return new UserStoreMemory();
    }

    @Bean
    @Profile("!memory-db")
    public UserStore userStoreDynamo() {
        return new UserStoreDynamo(DynamoDbClient.create());
    }
}
