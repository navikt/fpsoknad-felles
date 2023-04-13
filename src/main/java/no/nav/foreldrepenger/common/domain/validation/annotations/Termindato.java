package no.nav.foreldrepenger.common.domain.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import no.nav.foreldrepenger.common.domain.validation.TermindatoValidator;

@NotNull
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TermindatoValidator.class)
@Documented
@ReportAsSingleViolation
public @interface Termindato {
    String message() default "Termindato [${validatedValue}]  må være satt og kan ikke være lenger tilbake i tid enn {weeks} uker";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int weeks() default 3;
}
