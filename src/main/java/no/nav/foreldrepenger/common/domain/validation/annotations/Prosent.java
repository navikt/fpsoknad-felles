package no.nav.foreldrepenger.common.domain.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

import no.nav.foreldrepenger.common.domain.validation.ProsentValidator;

@Constraint(validatedBy = ProsentValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Prosent {

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    public double min() default 0d;

    public double max() default 100d;

    String message() default "Prosentandel må være mellom 0 og 100, var {validatedValue}";
}
