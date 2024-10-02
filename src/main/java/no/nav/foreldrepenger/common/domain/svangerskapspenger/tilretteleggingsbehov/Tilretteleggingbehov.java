package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilretteleggingsbehov;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

public record Tilretteleggingbehov(Arbeidsforhold arbeidsforhold,
                                   LocalDate behovForTilretteleggingFom,
                                   List<Tilrettelegging> tilrettelegginger,
                                   List<VedleggReferanse> vedlegg) {


    @JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = HelTilrettelegging.class, name = "hel"),
            @JsonSubTypes.Type(value = DelvisTilrettelegging.class, name = "delvis"),
            @JsonSubTypes.Type(value = IngenTilrettelegging.class, name = "ingen")
    })
    public interface Tilrettelegging {
    }

    public record HelTilrettelegging(@NotNull LocalDate tilrettelagtArbeidFom) implements Tilrettelegging {
    }
    public record DelvisTilrettelegging(@NotNull LocalDate tilrettelagtArbeidFom, @NotNull @Min(0) @Max(100) Double stillingsprosent) implements Tilrettelegging {
    }
    public record IngenTilrettelegging(@NotNull LocalDate slutteArbeidFom) implements Tilrettelegging {
    }

}
