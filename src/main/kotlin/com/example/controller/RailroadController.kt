package com.example.controller;

import com.example.model.Cart;
import com.example.model.Train;
import com.example.service.CartService
import com.example.service.sortService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject

@Controller("/train")
class RailroadController @Inject constructor(private val cartService: CartService) {

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCarts(@QueryValue sort: Boolean?): HttpResponse<List<Cart>> {
        val carts = cartService.findAll()
        return HttpResponse.ok(sortService(Train(carts = carts), sort))
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(@PathVariable id: String): HttpResponse<Cart> {
        val cart = cartService.findById(id)
        return HttpResponse.ok(cart)
    }

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createCart(@Body cart: Cart): HttpResponse<Cart> {
        val savedCart = cartService.save(cart)
        return HttpResponse.created(savedCart)
    }

    @Put("/{id}")
    fun update(@PathVariable id: String, @Body cart: Cart): HttpResponse<Cart> {
        val existingCart = cartService.findById(id) ?: return HttpResponse.notFound()
        val updatedCart = cartService.update(cart.copy(id = existingCart.id))
        return HttpResponse.ok(updatedCart)
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: String): HttpResponse<Any> {
        cartService.deleteById(id)
        return HttpResponse.noContent()
    }
}