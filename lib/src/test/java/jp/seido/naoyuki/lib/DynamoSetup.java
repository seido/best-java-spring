package jp.seido.naoyuki.lib;

import java.net.URI;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public class DynamoSetup implements BeforeAllCallback, AfterAllCallback {

    private DynamoDBProxyServer server;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(
          new String[]{"-inMemory", "-port", port});
        server.start();

        System.setProperty("aws.region", "ap-northeast-1");
        System.setProperty("aws.accessKeyId", "dummy");
        System.setProperty("aws.secretAccessKey", "dummy");
        System.setProperty("aws.endpointUrlDynamodb", "http://localhost:8000");
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .endpointOverride(new URI("http://localhost:" + port))
            .build();
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName("User")
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("id").attributeType(ScalarAttributeType.S).build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName("id").keyType(KeyType.HASH).build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L).writeCapacityUnits(10L).build())
                .build();
        dynamoDbClient.createTable(request);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        server.stop();
    }

}
