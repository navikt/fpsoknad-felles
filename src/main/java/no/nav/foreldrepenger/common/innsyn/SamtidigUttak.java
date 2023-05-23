package no.nav.foreldrepenger.common.innsyn;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record SamtidigUttak(@JsonValue BigDecimal value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SamtidigUttak { // NOSONAR
    }
}
