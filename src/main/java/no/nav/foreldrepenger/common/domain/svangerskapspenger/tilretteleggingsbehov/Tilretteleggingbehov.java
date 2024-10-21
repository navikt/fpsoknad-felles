package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilretteleggingsbehov;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

public record Tilretteleggingbehov(@Valid @NotNull Arbeidsforhold arbeidsforhold,
                                   @NotNull LocalDate behovForTilretteleggingFom,
                                   @Size(max = 20) List<@Valid @NotNull Tilrettelegging> tilrettelegginger,
                                   List<VedleggReferanse> vedlegg) {

    @JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Tilrettelegging.Hel.class, name = "hel"),
            @JsonSubTypes.Type(value = Tilrettelegging.Delvis.class, name = "delvis"),
            @JsonSubTypes.Type(value = Tilrettelegging.Ingen.class, name = "ingen")
    })
    public interface Tilrettelegging {
        record Hel(@NotNull LocalDate fom) implements Tilrettelegging {}
        record Delvis(@NotNull LocalDate fom, @NotNull @Min(0) @Max(100) Double stillingsprosent) implements Tilrettelegging {}
        record Ingen(@NotNull LocalDate fom) implements Tilrettelegging {}
    }
}
