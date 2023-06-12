package no.nav.foreldrepenger.common.innsyn.svp;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.innsyn.Arbeidstidprosent;

public record SvpPeriode(LocalDate fom,
                         TilretteleggingType tilreggeleggingTypeSøkt,
                         Arbeidstidprosent arbeidstidprosent,
                         ResultatType resultatType,
                         Utbetalingsgrad utbetalingsgrad) {

    public SvpPeriode {
        if (ResultatType.INNVILGET.equals(resultatType) != utbetalingsgrad.harUtbetaling()) {
            throw new IllegalArgumentException("Forventer bare utbetaling ved innvilget");
        }
    }

    public record Utbetalingsgrad(@JsonValue BigDecimal value) {
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public Utbetalingsgrad { // NOSONAR
        }

        public boolean harUtbetaling() {
            return value.compareTo(BigDecimal.ZERO) > 0;
        }
    }

    public enum ResultatType {
        INNVILGET,
        AVSLAG_SØKNADSFRIST,
        AVSLAG_ANNET
        //TODO utvide hvis det finnes flere
    }
}
