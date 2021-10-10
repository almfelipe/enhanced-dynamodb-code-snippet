package com.almfelipe.dynamodb.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.accesskey}")
    private String accesskey;

    @Value("${amazon.dynamodb.secretkey}")
    private String secretkey;

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {

        final var awsBasicCredentials = AwsBasicCredentials.create(accesskey, secretkey);

        final var dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .endpointOverride(URI.create(amazonDynamoDBEndpoint))
                .region(Region.SA_EAST_1)
                .build();

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
