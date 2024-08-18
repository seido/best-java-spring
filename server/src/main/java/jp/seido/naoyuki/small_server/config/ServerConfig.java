package jp.seido.naoyuki.small_server.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import jp.seido.naoyuki.lib.service.UserStore;
import jp.seido.naoyuki.lib.service.UserStoreDb;
import jp.seido.naoyuki.lib.service.UserStoreDynamo;
import jp.seido.naoyuki.lib.service.UserStoreMemory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class ServerConfig {

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    @Primary
    @Profile("rdb-db")
    public UserStore userStoreInRdb() {
        return beanFactory.getBean(UserStoreDb.class);
    }

    @Bean
    @Primary
    @Profile("memory-db")
    public UserStore userStoreInMemory() {
        return new UserStoreMemory();
    }

    @Bean
    @Primary
    @Profile("!memory-db & !rdb-db")
    public UserStore userStoreDynamo() {
        return new UserStoreDynamo(DynamoDbClient.create());
    }
}
