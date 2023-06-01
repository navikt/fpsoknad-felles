package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HelTilrettelegging.class, name = "hel"),
        @JsonSubTypes.Type(value = DelvisTilrettelegging.class, name = "delvis"),
        @JsonSubTypes.Type(value = IngenTilrettelegging.class, name = "ingen")
})
public abstract class Tilrettelegging {

    @Valid
    private final Arbeidsforhold arbeidsforhold;
    @NotNull
    private final LocalDate behovForTilretteleggingFom;
    private final List<@Valid VedleggReferanse> vedlegg;

    protected Tilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom, List<VedleggReferanse> vedlegg) {
        this.arbeidsforhold = arbeidsforhold;
        this.behovForTilretteleggingFom = behovForTilretteleggingFom;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }

    public Arbeidsforhold getArbeidsforhold() {
        return arbeidsforhold;
    }

    public LocalDate getBehovForTilretteleggingFom() {
        return behovForTilretteleggingFom;
    }

    public List<VedleggReferanse> getVedlegg() {
        return vedlegg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tilrettelegging that = (Tilrettelegging) o;
        return Objects.equals(arbeidsforhold, that.arbeidsforhold) && Objects.equals(behovForTilretteleggingFom, that.behovForTilretteleggingFom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arbeidsforhold, behovForTilretteleggingFom);
    }

    @Override
    public String toString() {
        return "Tilrettelegging{" +
                "arbeidsforhold=" + arbeidsforhold +
                ", behovForTilretteleggingFom=" + behovForTilretteleggingFom +
                '}';
    }
}
