package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

public final class IngenTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate slutteArbeidFom;

    public IngenTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom, LocalDate slutteArbeidFom) {
        super(arbeidsforhold, behovForTilretteleggingFom);
        this.slutteArbeidFom = slutteArbeidFom;
    }

    public LocalDate getSlutteArbeidFom() {
        return slutteArbeidFom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IngenTilrettelegging that = (IngenTilrettelegging) o;
        return Objects.equals(slutteArbeidFom, that.slutteArbeidFom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), slutteArbeidFom);
    }

    @Override
    public String toString() {
        return "IngenTilrettelegging{" +
                "slutteArbeidFom=" + slutteArbeidFom +
                '}' + super.toString();
    }
}
