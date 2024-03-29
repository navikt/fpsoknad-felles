package no.nav.foreldrepenger.common.domain.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.LukketPeriodeMedVedlegg;
import no.nav.foreldrepenger.common.domain.validation.annotations.LukketPeriode;

public class LukketPeriodeMedVedleggValidator implements ConstraintValidator<LukketPeriode, LukketPeriodeMedVedlegg> {

    private static final Logger LOG = LoggerFactory.getLogger(LukketPeriodeMedVedleggValidator.class);

    @Override
    public boolean isValid(LukketPeriodeMedVedlegg periode, ConstraintValidatorContext context) {
        LOG.trace("Validerer periode {} ({}-{})", periode.getClass().getSimpleName(), periode.getFom(), periode.getTom());
        if (periode.getFom() == null) {
            return false;
        }
        if (periode.getTom() == null) {
            return false;
        }
        return !periode.getFom().isAfter(periode.getTom());
    }
}
