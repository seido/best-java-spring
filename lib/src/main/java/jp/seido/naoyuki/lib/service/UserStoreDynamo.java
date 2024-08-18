package jp.seido.naoyuki.lib.service;

import java.util.Map;
import java.util.UUID;

import jp.seido.naoyuki.lib.entity.User;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@AllArgsConstructor
public class UserStoreDynamo implements UserStore {

    private final DynamoDbClient dynamoDbClient;

    private static final String TABLE_NAME = "User";

    public User register(String username, String password) {
        String id = UUID.randomUUID().toString();
        User user = User.builder().id(id).username(username).password(password).build();
        dynamoDbClient.putItem(PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(Map.of(
                "id", AttributeValue.builder().s(user.getId()).build(),
                "username", AttributeValue.builder().s(user.getUsername()).build(),
                "password", AttributeValue.builder().s(user.getPassword()).build()
            ))
            .build());
        return user;
    }

    public User getUserById(String id) {
       GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder()
            .tableName(TABLE_NAME)
            .key(Map.of("id", AttributeValue.builder().s(id).build()))
            .build());
        
        Map<String, AttributeValue> item = response.item();
        if (item == null || item.size() == 0) {
            return null;
        }

        return User.builder()
            .id(item.get("id").s())
            .username(item.get("username").s())
            .password(item.get("password").s())
            .build();
    }
}
