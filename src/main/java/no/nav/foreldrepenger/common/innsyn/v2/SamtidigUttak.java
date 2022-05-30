package no.nav.foreldrepenger.common.innsyn.v2;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record SamtidigUttak(@JsonValue BigDecimal value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SamtidigUttak {
    }

    @Override
    public BigDecimal value() { // NOSONAR: Her overrider vi default getter fra record fordi den propagerer annoteringer fra field. Vi ønsker ikke @JsonValue på getter.
        return value;
    }
}
