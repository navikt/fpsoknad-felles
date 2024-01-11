package no.nav.foreldrepenger.common.domain.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import no.nav.foreldrepenger.common.domain.validation.UtenOverlappValidator;

@Constraint(validatedBy = UtenOverlappValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UtenOverlapp {

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    String message() default "Nei gi deg, da";
}
