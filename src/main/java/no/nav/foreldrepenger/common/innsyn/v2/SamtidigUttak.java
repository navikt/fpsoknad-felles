package no.nav.foreldrepenger.common.innsyn.v2;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

record SamtidigUttak(@JsonValue BigDecimal value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    SamtidigUttak {
    }

    @Override
    public BigDecimal value() {
        return value;
    }
}