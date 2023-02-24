package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class DelvisTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate tilrettelagtArbeidFom;
    @NotNull
    private final ProsentAndel stillingsprosent;

    public DelvisTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom,
            LocalDate tilrettelagtArbeidFom,
            ProsentAndel stillingsprosent, List<VedleggReferanse> vedlegg) {
        super(arbeidsforhold, behovForTilretteleggingFom, vedlegg);
        this.tilrettelagtArbeidFom = tilrettelagtArbeidFom;
        this.stillingsprosent = stillingsprosent;
    }

    public LocalDate getTilrettelagtArbeidFom() {
        return tilrettelagtArbeidFom;
    }

    public ProsentAndel getStillingsprosent() {
        return stillingsprosent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DelvisTilrettelegging that = (DelvisTilrettelegging) o;
        return Objects.equals(tilrettelagtArbeidFom, that.tilrettelagtArbeidFom) && Objects.equals(stillingsprosent, that.stillingsprosent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tilrettelagtArbeidFom, stillingsprosent);
    }

    @Override
    public String toString() {
        return "DelvisTilrettelegging{" +
                "tilrettelagtArbeidFom=" + tilrettelagtArbeidFom +
                ", stillingsprosent=" + stillingsprosent +
                '}' + super.toString();
    }
}
