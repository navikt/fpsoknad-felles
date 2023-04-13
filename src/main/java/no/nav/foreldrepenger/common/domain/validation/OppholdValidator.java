package no.nav.foreldrepenger.common.domain.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.validation.annotations.Opphold;

public class OppholdValidator implements ConstraintValidator<Opphold, List<Utenlandsopphold>> {

    private static final Logger LOG = LoggerFactory.getLogger(OppholdValidator.class);

    private boolean fortid;

    @Override
    public void initialize(Opphold constraintAnnotation) {
        this.fortid = constraintAnnotation.fortid();
    }

    @Override
    public boolean isValid(List<Utenlandsopphold> alleOpphold, ConstraintValidatorContext context) {
        for (var oppholdUnderValidering : alleOpphold) {
            if (validerFortid(oppholdUnderValidering)) {
                LOG.debug("Periode {} er ikke utelukkende i fortiden", oppholdUnderValidering);
                errorMessageFortidFremtid(context, oppholdUnderValidering, "er ikke utelukkende i fortiden");
                return false;
            }
            if (validerFramtid(oppholdUnderValidering)) {
                LOG.debug("Periode {} er ikke i utelukkende framtiden", oppholdUnderValidering);
                errorMessageFortidFremtid(context, oppholdUnderValidering, "er ikke i utelukkende framtiden");
                return false;
            }
        }

        // Alle perioder trenger bare sammenlignes en gang hverandre
        var oppholdsperioderSomIkkeErValidert = new ArrayList<>(alleOpphold);
        while (!oppholdsperioderSomIkkeErValidert.isEmpty()) {
            var oppholdUnderValidering = oppholdsperioderSomIkkeErValidert.remove(0);
            for (var opphold : oppholdsperioderSomIkkeErValidert) {
                if (erOverlappende(oppholdUnderValidering, opphold)) {
                    LOG.debug("Fant overlapp mellom periodene {} og {}", oppholdUnderValidering.varighet(), opphold.varighet());
                    errorMessageOverlap(context, oppholdUnderValidering, opphold);
                    return false;
                }
            }
        }
        LOG.info("Periode validert OK");
        return true;
    }

    public static boolean erOverlappende(Utenlandsopphold førstePeriode, Utenlandsopphold annenPeriode) {
        // Siden vi ikke kan garantere at listen er sortert, så må begge disse være sanne
        return førstePeriode.tom().isAfter(annenPeriode.fom()) &&
                annenPeriode.tom().isAfter(førstePeriode.fom());
    }

    private boolean validerFramtid(Utenlandsopphold opphold) {
        return !fortid && isBeforeNow(opphold);
    }

    private boolean validerFortid(Utenlandsopphold opphold) {
        return fortid && isAfterNow(opphold);
    }

    private static boolean isAfterNow(Utenlandsopphold opphold) {
        return opphold.fom().isAfter(LocalDate.now()) ||
                opphold.tom().isAfter(LocalDate.now());
    }

    private static boolean isBeforeNow(Utenlandsopphold opphold) {
        return opphold.fom().isBefore(LocalDate.now()) ||
                opphold.tom().isBefore(LocalDate.now());
    }

    private static void errorMessageFortidFremtid(ConstraintValidatorContext context, Utenlandsopphold opphold, String txt) {
        var hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
//        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext.addExpressionVariable("fra", opphold.fom())
                .addExpressionVariable("til", opphold.tom())
                .addExpressionVariable("txt", txt)
                .buildConstraintViolationWithTemplate("Perioden ${fra} - ${til} ${txt}")
                .enableExpressionLanguage();
    }

    private static void errorMessageOverlap(ConstraintValidatorContext context, Utenlandsopphold periode1, Utenlandsopphold periode2) {
        var hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
//        hibernateContext.disableDefaultConstraintViolation();
        hibernateContext
                .addExpressionVariable("fra1", periode1.fom())
                .addExpressionVariable("til1", periode1.tom())
                .addExpressionVariable("fra2", periode2.fom())
                .addExpressionVariable("til2", periode2.tom())
                .buildConstraintViolationWithTemplate("Periodene ${fra1} - ${til1} og ${fra2} - ${til2} overlapper")
                .enableExpressionLanguage();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [fortid=" + fortid + "]";
    }
}
