package com.persons.finder.domain.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import javax.validation.ConstraintValidatorContext

class LatitudeValidatorTest {

    private val validator = LatitudeValidator()
    private val context = mock(ConstraintValidatorContext::class.java)

    @Test
    fun `valid latitude should pass`() {
        assertTrue(validator.isValid(0.0, context))
        assertTrue(validator.isValid(45.0, context))
        assertTrue(validator.isValid(-90.0, context))
        assertTrue(validator.isValid(90.0, context))
    }

    @Test
    fun `invalid latitude should fail`() {
        assertFalse(validator.isValid(-91.0, context))
        assertFalse(validator.isValid(91.0, context))
        assertFalse(validator.isValid(Double.NaN, context))
        assertFalse(validator.isValid(null, context))
    }
}