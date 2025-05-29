package com.persons.finder.domain.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [LatitudeValidator::class])
annotation class ValidLatitude(
    val message: String = "Invalid latitude: must be between -90.0 and 90.0",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class LatitudeValidator : ConstraintValidator<ValidLatitude, Double> {
    override fun isValid(value: Double?, context: ConstraintValidatorContext): Boolean {
        return value != null && value in -90.0..90.0
    }
}