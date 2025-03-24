package no.nav.foreldrepenger.common.innsyn;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public record Gradering(@NotNull Arbeidstidprosent arbeidstidprosent, @NotNull Aktivitet aktivitet) {

    @JsonCreator
    public Gradering(@JsonProperty("arbeidstidprosent") BigDecimal arbeidstidprosent,
                     @JsonProperty("aktivitet") Aktivitet aktivitet) {
        this(new Arbeidstidprosent(arbeidstidprosent), aktivitet);
    }
}
