package no.nav.foreldrepenger.common.dto.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import no.nav.foreldrepenger.common.dto.validation.RettigheterValidator;

@Constraint(validatedBy = RettigheterValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Rettighet {

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    String message() default "{ytelse.rettighet.ugyldig}";
}
