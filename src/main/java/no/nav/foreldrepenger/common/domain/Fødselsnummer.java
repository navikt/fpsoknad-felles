package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.util.StringUtil.partialMask;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.domain.felles.Kjønn;

public record Fødselsnummer(@JsonValue String value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Fødselsnummer {
        Objects.requireNonNull(value, "Fødselsnummer kan ikke være null");
    }

    public Kjønn kjønn() {
        if (value != null && value.length() == 11) {
            return Integer.valueOf(value.charAt(8)) % 2 == 0 ? Kjønn.K : Kjønn.M;
        }
        return Kjønn.U;
    }

    @Override
    public String value() { // NOSONAR: Annotering i record field blir også lagt på getter
        return value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [fnr=" + partialMask(value) + "]";
    }
}
