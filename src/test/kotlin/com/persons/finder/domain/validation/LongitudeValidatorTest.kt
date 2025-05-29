package com.persons.finder.domain.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import javax.validation.ConstraintValidatorContext

class LongitudeValidatorTest {

    private val validator = LongitudeValidator()
    private val context = mock(ConstraintValidatorContext::class.java)

    @Test
    fun `valid longitude should pass`() {
        assertTrue(validator.isValid(0.0, context))
        assertTrue(validator.isValid(45.0, context))
        assertTrue(validator.isValid(-180.0, context))
        assertTrue(validator.isValid(180.0, context))
    }

    @Test
    fun `invalid longitude should fail`() {
        assertFalse(validator.isValid(181.0, context))
        assertFalse(validator.isValid(-181.0, context))
        assertFalse(validator.isValid(Double.NaN, context))
        assertFalse(validator.isValid(null, context))
    }
}