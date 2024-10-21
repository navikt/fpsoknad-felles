package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

public final class HelTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate tilrettelagtArbeidFom;

    public HelTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom,
            LocalDate tilrettelagtArbeidFom, List<VedleggReferanse> vedlegg) {
        super(arbeidsforhold, behovForTilretteleggingFom, vedlegg);
        this.tilrettelagtArbeidFom = tilrettelagtArbeidFom;
    }

    public LocalDate getTilrettelagtArbeidFom() {
        return tilrettelagtArbeidFom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HelTilrettelegging that = (HelTilrettelegging) o;
        return Objects.equals(tilrettelagtArbeidFom, that.tilrettelagtArbeidFom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tilrettelagtArbeidFom);
    }

    @Override
    public String toString() {
        return "HelTilrettelegging{" +
                "tilrettelagtArbeidFom=" + tilrettelagtArbeidFom +
                '}' + super.toString();
    }
}
