package com.example.repository

import com.example.model.Cart
import io.micronaut.context.annotation.Value
import jakarta.inject.Named
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.ScanRequest

@Singleton
class CartRepositoryImp(@Named("customDynamoDbClient") private val dynamoDbClient: DynamoDbClient) : CartRepository {

    @Value("\${aws.dynamodb.table-name}")
    lateinit var tableName: String

    override fun save(cart: Cart): Cart {
        val request = PutItemRequest.builder()
            .tableName(tableName)
            .item(
                mapOf(
                    "id" to AttributeValue.builder().s(cart.id).build(),
                    "name" to AttributeValue.builder().s(cart.name).build(),
                    "destination" to AttributeValue.builder().s(cart.destination).build(),
                    "receiver" to AttributeValue.builder().s(cart.receiver).build()
                )
            )
            .build()
        dynamoDbClient.putItem(request)
        return cart
    }

    override fun findById(id: String): Cart? {
        val request = GetItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.builder().s(id).build()))
            .build()
        val item = dynamoDbClient.getItem(request).item()
        return item?.let {
            Cart(
                id = it["id"]?.s() ?: "",
                name = it["name"]?.s() ?: "",
                destination = it["destination"]?.s() ?: "",
                receiver = it["receiver"]?.s() ?: "",
                classification = it["classification"]?.s() ?: "",
            )
        }
    }

    override fun findAll(): List<Cart> {
        val request = ScanRequest.builder().tableName(tableName).build()
        val result = dynamoDbClient.scan(request)
        return result.items().map {
            Cart(
                id = it["id"]?.s() ?: "",
                name = it["name"]?.s() ?: "",
                destination = it["destination"]?.s() ?: "",
                receiver = it["receiver"]?.s() ?: "",
                classification = it["classification"]?.s() ?: "",
            )
        }
    }

    override fun update(cart: Cart): Cart {
        return save(cart)
    }

    override fun deleteById(id: String) {
        val request = DeleteItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.builder().s(id).build()))
            .build()
        dynamoDbClient.deleteItem(request)
    }
}