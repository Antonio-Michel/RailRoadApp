package com.example.service

import com.example.model.Cart
import com.example.model.Train
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest(startApplication = false)
class SortServiceKtTest {

    @Test
    fun `should return a sorted train when sort is true`() {
        val inputTrain: Train = Train(
            carts = listOf(
                Cart("1", "engine", null, null, null),
                Cart("2", "Box Cart 1", "Houston", "FedEx", null),
                Cart("3", "Box Cart 2", "Chicago", "FedEx", null),
                Cart("4", "Box Cart 3", "Houston", "UPS", null),
                Cart("5", "Box Cart 4", "LA", "Old_Dominion", null),
                Cart("6", "Box Cart 5", "LA", "FedEx", null),
                Cart("7", "Box Cart 6", "Houston", "Old_Dominion", null)
            )
        )

        val expectedTrain = listOf(
            Cart("1", "engine", null, null, null),
            Cart("4", "Box Cart 3", "Houston", "UPS", null),
            Cart("2", "Box Cart 1", "Houston", "FedEx", null),
            Cart("7", "Box Cart 6", "Houston", "Old_Dominion", null),
            Cart("3", "Box Cart 2", "Chicago", "FedEx", null),
            Cart("6", "Box Cart 5", "LA", "FedEx", null),
            Cart("5", "Box Cart 4", "LA", "Old_Dominion", null)
        )

        Assertions.assertEquals(expectedTrain, sortService(inputTrain, true))
    }

    @Test
    fun `should return the train unsorted when sort is false`() {
        val inputTrain: Train = Train(
            carts = listOf(
                Cart("1", "engine", null, null, null),
                Cart("2", "Box Cart 1", "Houston", "FedEx", null),
                Cart("3", "Box Cart 2", "Chicago", "FedEx", null),
                Cart("4", "Box Cart 3", "Houston", "UPS", null),
                Cart("5", "Box Cart 4", "LA", "Old_Dominion", null),
                Cart("6", "Box Cart 5", "LA", "FedEx", null),
                Cart("7", "Box Cart 6", "Houston", "Old_Dominion", null)
            )
        )

        Assertions.assertEquals(inputTrain, Train(carts = sortService(inputTrain, false)))
    }
}