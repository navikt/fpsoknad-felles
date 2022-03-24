package no.nav.foreldrepenger.common.innsyn.v2;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Gradering(Arbeidstidprosent arbeidstidprosent) {

    @JsonCreator
    public Gradering(BigDecimal arbeidstidprosent) {
        this(new Arbeidstidprosent(arbeidstidprosent));
    }

    public record Arbeidstidprosent(@JsonValue BigDecimal value) {
        @Override
        public BigDecimal value() {
            return value;
        }
    }
}
