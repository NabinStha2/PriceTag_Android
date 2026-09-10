package com.example.price_tag

import com.example.price_tag.feature.checkout.presentation.model.AddressFormState
import com.example.price_tag.feature.checkout.presentation.validation.validate
import com.example.price_tag.feature.checkout.presentation.validation.validateCheckoutContactDetails
import org.junit.Assert
import org.junit.Test

class CheckoutValidationTest {

    private fun validAddress() = AddressFormState(
        street = "George Street",
        city = "Sydney",
        state = "NSW",
        country = "Australia",
        postalCode = "2000",
    )

    @Test
    fun `empty address reports every required field`() {
        val errors = AddressFormState().validate()

        Assert.assertFalse(errors.isValid)
        Assert.assertNotNull(errors.street)
        Assert.assertNotNull(errors.city)
        Assert.assertNotNull(errors.state)
        Assert.assertNotNull(errors.country)
        Assert.assertNotNull(errors.postalCode)
    }

    @Test
    fun `valid address allows empty optional fields`() {
        Assert.assertTrue(validAddress().validate().isValid)
    }

    @Test
    fun `street accepts trimmed maximum length and rejects overflow`() {
        val accepted = validAddress().copy(
            street = " ${"a".repeat(150)} ",
        )
        val rejected = validAddress().copy(
            street = "a".repeat(151),
        )

        Assert.assertTrue(accepted.validate().isValid)
        Assert.assertNotNull(rejected.validate().street)
    }

    @Test
    fun `optional label still has a length limit`() {
        val errors = validAddress().copy(label = "a".repeat(51))
            .validate()

        Assert.assertNotNull(errors.label)
    }

    @Test
    fun `whitespace contact details are rejected`() {
        val errors = validateCheckoutContactDetails(
            recipientName = "   ",
            phoneNumber = "   ",
        )

        Assert.assertNotNull(errors.recipientName)
        Assert.assertNotNull(errors.phoneNumber)
        Assert.assertFalse(errors.isValid)
    }

    @Test
    fun `contact fields accept maximum lengths and reject overflow`() {
        val accepted = validateCheckoutContactDetails(
            recipientName = "a".repeat(100),
            phoneNumber = "1".repeat(30),
        )
        val rejected = validateCheckoutContactDetails(
            recipientName = "a".repeat(101),
            phoneNumber = "1".repeat(31),
        )

        Assert.assertTrue(accepted.isValid)
        Assert.assertNotNull(rejected.recipientName)
        Assert.assertNotNull(rejected.phoneNumber)
    }
}