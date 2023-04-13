package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class IngenTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate slutteArbeidFom;

    public IngenTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom,
            LocalDate slutteArbeidFom, List<VedleggReferanse> vedlegg) {
        super(arbeidsforhold, behovForTilretteleggingFom, vedlegg);
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
