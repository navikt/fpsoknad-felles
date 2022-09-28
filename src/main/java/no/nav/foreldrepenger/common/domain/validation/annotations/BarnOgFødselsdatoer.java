package no.nav.foreldrepenger.common.domain.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import no.nav.foreldrepenger.common.domain.validation.BarnOgFødselsdatoerValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { BarnOgFødselsdatoerValidator.class })
@Documented
public @interface BarnOgFødselsdatoer {

    String message() default "Antall barn og antall fødselsdatoer må matche";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
