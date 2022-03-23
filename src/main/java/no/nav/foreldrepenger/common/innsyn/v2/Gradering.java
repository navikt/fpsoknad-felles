package no.nav.foreldrepenger.common.innsyn.v2;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

record Gradering(Arbeidstidprosent arbeidstidprosent) {

    @JsonCreator
    Gradering(BigDecimal arbeidstidprosent) {
        this(new Arbeidstidprosent(arbeidstidprosent));
    }

    static record Arbeidstidprosent(@JsonValue BigDecimal value) {
        @Override
        public BigDecimal value() {
            return value;
        }
    }
}