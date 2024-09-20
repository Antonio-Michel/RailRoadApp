package com.example.service

import com.example.model.Cart
import com.example.repository.CartRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.UUID

@MicronautTest(startApplication = false)
class CartServiceTest {

    @Mock
    private lateinit var cartRepository: CartRepository

    @InjectMocks
    lateinit var cartService: CartService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    private val testUUID = UUID.randomUUID().toString()
    private val testCart = Cart(testUUID, "Box Cart 1", "Houston", "FedEx", null)

    // This test checks for the initial database to be empty, given this case in which the same database is being
    // used for both the running application and it's tests it will only pass the first time it's called so it
    // has been commented.
    /*
    @Test

    fun `should return an empty cart list`() {
        val testCarts = cartRepository.findAll()

        val expectedCarts = listOf<Cart>()

        Assertions.assertEquals(testCarts, expectedCarts)
    }
     */

    @Test
    fun `should add one cart`() {
        `when`(cartRepository.save(testCart)).thenReturn(testCart)

        val savedCart = cartService.save(testCart)

        Assertions.assertEquals(testCart, savedCart)
        verify(cartRepository, times(1)).save(testCart)
    }


    @Test
    fun `should find the cart added by its ID`() {
        `when`(cartRepository.findById(testUUID)).thenReturn(testCart)

        val foundItem = cartService.findById(testUUID)

        Assertions.assertEquals(testCart, foundItem)
        verify(cartRepository, times(1)).findById(testUUID)
    }

    @Test
    fun `test find all carts`() {
        val carts = listOf(
            testCart,
            Cart(UUID.randomUUID().toString(), "Box Cart 2", "Chicago", "FedEx", null)
        )
        `when`(cartRepository.findAll()).thenReturn(carts)

        val foundCarts = cartService.findAll()

        Assertions.assertEquals(carts, foundCarts)
        verify(cartRepository, times(1)).findAll()
    }

    @Test
    fun `test update item`() {
        `when`(cartRepository.update(testCart)).thenReturn(testCart)

        val updatedItem = cartService.update(testCart)

        Assertions.assertEquals(testCart, updatedItem)
        verify(cartRepository, times(1)).update(testCart)
    }

    @Test
    fun `test delete by id`() {
        doNothing().`when`(cartRepository).deleteById(testUUID)

        cartService.deleteById(testUUID)

        verify(cartRepository, times(1)).deleteById(testUUID)
    }
    /*
    @Test
    fun `should update the second cart`() {
        val newName: String = "Box Cart 4"

        val cartToUpdate = cartRepository.findById(testUUID)


        cartRepository.update(
            Cart(
                testUUID,
                newName,
                cartToUpdate?.destination,
                cartToUpdate?.receiver,
                cartToUpdate?.classification
            )
        )

        Assertions.assertTrue(cartRepository.findById(testUUID)?.name == newName)
    }

    @Test
    fun `should delete a cart from the list`() {
        val beforeDbSize = cartRepository.findAll().size

        cartRepository.deleteById(testUUID)

        val afterDbSize = cartService.findAll().size

        Assertions.assertTrue(beforeDbSize == afterDbSize - 1)
    }
     */
}