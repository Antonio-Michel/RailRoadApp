package com.example.service;

import com.example.model.Cart;
import com.example.model.Train
import com.example.repository.CartRepository;
import jakarta.inject.Singleton;
import java.util.UUID

@Singleton
class CartService(private val cartRepository: CartRepository) {

    fun save(cart: Cart): Cart = cartRepository.save(cart)

    fun findById(id: String): Cart? = cartRepository.findById(id)

    fun findAll(): List<Cart> = cartRepository.findAll()

    fun update(cart: Cart): Cart = cartRepository.update(cart)

    fun deleteById(id: String) = cartRepository.deleteById(id)
}
