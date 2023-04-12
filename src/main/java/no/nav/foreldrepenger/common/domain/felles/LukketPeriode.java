package no.nav.foreldrepenger.common.domain.felles;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import no.nav.foreldrepenger.common.domain.validation.annotations.Periode;


@JsonPropertyOrder({ "fom", "tom" })
@Periode
public record LukketPeriode(@NotNull LocalDate fom, @NotNull LocalDate tom) {
    @JsonIgnore
    public boolean isWithinPeriod(LocalDate dato) {
        return dato.isAfter(fom().minusDays(1)) && dato.isBefore(tom().plusDays(1));
    }

}
