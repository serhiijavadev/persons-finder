package com.persons.finder.domain.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [LongitudeValidator::class])
annotation class ValidLongitude(
    val message: String = "Invalid longitude: must be between -180.0 and 180.0",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class LongitudeValidator : ConstraintValidator<ValidLongitude, Double> {
    override fun isValid(value: Double?, context: ConstraintValidatorContext): Boolean {
        return value != null && value in -180.0..180.0
    }
}