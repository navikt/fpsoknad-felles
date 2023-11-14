package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HelTilrettelegging.class, name = "hel"),
        @JsonSubTypes.Type(value = DelvisTilrettelegging.class, name = "delvis"),
        @JsonSubTypes.Type(value = IngenTilrettelegging.class, name = "ingen")
})
public abstract sealed class Tilrettelegging permits HelTilrettelegging, DelvisTilrettelegging, IngenTilrettelegging {

    @Valid
    private final Arbeidsforhold arbeidsforhold;
    @NotNull
    private final LocalDate behovForTilretteleggingFom;

    protected Tilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom) {
        this.arbeidsforhold = arbeidsforhold;
        this.behovForTilretteleggingFom = behovForTilretteleggingFom;
    }

    public Arbeidsforhold getArbeidsforhold() {
        return arbeidsforhold;
    }

    public LocalDate getBehovForTilretteleggingFom() {
        return behovForTilretteleggingFom;
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
