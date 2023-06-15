package no.nav.foreldrepenger.common.innsyn.svp;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record PeriodeResultat(ResultatType resultatType,
                              Utbetalingsgrad utbetalingsgrad) {

    public record Utbetalingsgrad(@JsonValue BigDecimal value) {
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public Utbetalingsgrad { // NOSONAR
        }
    }

    public enum ResultatType {
        INNVILGET,
        AVSLAG_SØKNADSFRIST,
        AVSLAG_ANNET
    }
}
