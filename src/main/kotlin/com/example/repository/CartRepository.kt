package com.example.repository

import com.example.model.Cart
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
interface CartRepository {
    fun save(cart: Cart): Cart
    fun findById(id: String): Cart?
    fun findAll(): List<Cart>
    fun update(cart: Cart): Cart
    fun deleteById(id: String)
}