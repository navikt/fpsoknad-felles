package no.nav.foreldrepenger.common.domain.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.validation.annotations.UtenOverlapp;

public class UtenOverlappValidator implements ConstraintValidator<UtenOverlapp, List<Utenlandsopphold.Opphold>> {
    @Override
    public boolean isValid(List<Utenlandsopphold.Opphold> utenlandsopphold, ConstraintValidatorContext context) {
        if (erOverlappendePerioder(utenlandsopphold, context)) {
            return false;
        }
        return true;
    }

    private static boolean erOverlappendePerioder(List<Utenlandsopphold.Opphold> utenlandsopphold, ConstraintValidatorContext context) {
        var oppholdsperioderSomIkkeErValidert = new ArrayList<>(utenlandsopphold);
        while (!oppholdsperioderSomIkkeErValidert.isEmpty()) {
            var oppholdUnderValidering = oppholdsperioderSomIkkeErValidert.remove(0);
            for (var opphold : oppholdsperioderSomIkkeErValidert) {
                if (erOverlappende(oppholdUnderValidering, opphold)) {
                    errorMessageOverlap(context, oppholdUnderValidering, opphold);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean erOverlappende(Utenlandsopphold.Opphold førstePeriode, Utenlandsopphold.Opphold annenPeriode) {
        return førstePeriode.tom().isAfter(annenPeriode.fom()) && annenPeriode.tom().isAfter(førstePeriode.fom());
    }

    private static void errorMessageOverlap(ConstraintValidatorContext context, Utenlandsopphold.Opphold periode1, Utenlandsopphold.Opphold periode2) {
        var hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
        hibernateContext
                .addExpressionVariable("fra1", periode1.fom())
                .addExpressionVariable("til1", periode1.tom())
                .addExpressionVariable("fra2", periode2.fom())
                .addExpressionVariable("til2", periode2.tom())
                .buildConstraintViolationWithTemplate("Periodene ${fra1} - ${til1} og ${fra2} - ${til2} overlapper")
                .enableExpressionLanguage();
    }
}
