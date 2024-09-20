package com.example.controller

import com.example.model.Cart
import com.example.service.CartService
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.UUID

@MicronautTest(startApplication = false)
class RailroadControllerTest {

    @Mock
    private lateinit var cartService: CartService

    @InjectMocks
    private lateinit var cartController: RailroadController

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    private val testUUID = UUID.randomUUID().toString()
    private val testCart = Cart(testUUID, "Box Cart 1", "Houston", "FedEx", null)

    @Test
    fun `test create cart`() {
        `when`(cartService.save(testCart)).thenReturn(testCart)

        val response = cartController.createCart(testCart)

        assertEquals(HttpStatus.CREATED, response.status)
        assertEquals(testCart, response.body.get())
        verify(cartService, times(1)).save(testCart)
    }

    @Test
    fun `test get cart by id`() {
        `when`(cartService.findById(testUUID)).thenReturn(testCart)

        val response = cartController.getById(testUUID)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(testCart, response.body.get())
        verify(cartService, times(1)).findById(testUUID)
    }

    @Test
    fun `test get all carts`() {
        val carts = listOf(
            testCart,
            Cart(UUID.randomUUID().toString(), "Box Cart 2", "Chicago", "FedEx", null)
        )
        `when`(cartService.findAll()).thenReturn(carts)

        val response = cartController.getCarts(false)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(carts, response.body.get())
        verify(cartService, times(1)).findAll()
    }

    @Test
    fun `test update cart`() {
        `when`(cartService.findById(testUUID)).thenReturn(testCart)
        `when`(cartService.update(testCart)).thenReturn(testCart)

        val response = cartController.update(testUUID, testCart)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(testCart, response.body.get())
        verify(cartService, times(1)).findById(testUUID)
        verify(cartService, times(1)).update(testCart)
    }

    @Test
    fun `test delete cart`() {
        val cartId = testUUID
        doNothing().`when`(cartService).deleteById(cartId)

        val response = cartController.delete(cartId)

        assertEquals(HttpStatus.NO_CONTENT, response.status)
        verify(cartService, times(1)).deleteById(cartId)
    }
}