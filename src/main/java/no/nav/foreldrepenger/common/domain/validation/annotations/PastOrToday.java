package no.nav.foreldrepenger.common.domain.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import no.nav.foreldrepenger.common.domain.validation.PastOrTodayValidator;

@Target({ ElementType.FIELD, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastOrTodayValidator.class)
@Documented
@ReportAsSingleViolation
public @interface PastOrToday {
    String message()

    default "{ytelse.relasjontilbarn.framtidig.terminbekreftelse.fortid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
