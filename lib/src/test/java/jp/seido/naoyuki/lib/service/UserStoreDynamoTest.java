package jp.seido.naoyuki.lib.service;

import java.net.URI;

import org.junit.jupiter.api.extension.ExtendWith;

import jp.seido.naoyuki.lib.DynamoSetup;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ExtendWith(DynamoSetup.class)
public class UserStoreDynamoTest extends UserStoreTest {

    @Override
    UserStore createUserStore() {
        try {
            System.setProperty("aws.region", "ap-northeast-1");
            System.setProperty("aws.accessKeyId", "dummy");
            System.setProperty("aws.secretAccessKey", "dummy");
            System.setProperty("aws.endpointUrlDynamodb", "http://localhost:8000");
            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .endpointOverride(new URI("http://localhost:8000"))
                    .build();
        return new UserStoreDynamo(dynamoDbClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
