package com.example.service

import com.example.model.Cart
import com.example.model.Train

fun sortService(train: Train, sort: Boolean?): List<Cart> {

    val destinationOrder = listOf("Houston", "Chicago", "LA")
    val carrierOrder = listOf("UPS", "FedEx", "Old_Dominion")

    if (sort == true) {

        val cartsToSort: MutableList<Cart> = train.carts.toMutableList()

        return cartsToSort.sortedWith(compareBy<Cart> {
            destinationOrder.indexOf(it.destination)
        }.thenBy {
            carrierOrder.indexOf(it.receiver)
        }).toList()
    }
    return train.carts.toList()
}