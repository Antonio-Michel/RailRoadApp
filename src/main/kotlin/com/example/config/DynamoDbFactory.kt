package com.example.config

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import jakarta.inject.Named
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import java.net.URI

@Factory
class DynamoDbFactory {

    @Value("\${aws.dynamodb.endpoint-override}")
    lateinit var endpoint: String

    @Value("\${aws.dynamodb.region}")
    lateinit var region: String

    @Value("\${aws.dynamodb.access-key-id}")
    lateinit var accessKeyId: String

    @Value("\${aws.dynamodb.secret-access-key}")
    lateinit var secretAccessKey: String

    @Bean
    @Singleton
    @Named("customDynamoDbClient")
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .endpointOverride(URI.create(endpoint))
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                )
            )
            .overrideConfiguration(
                ClientOverrideConfiguration.builder().build()
            )
            .build()
    }
}
