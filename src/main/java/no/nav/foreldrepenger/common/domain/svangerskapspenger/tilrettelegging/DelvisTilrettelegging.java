package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

public final class DelvisTilrettelegging extends Tilrettelegging {

    @NotNull
    private final LocalDate tilrettelagtArbeidFom;
    @NotNull
    @Valid
    private final ProsentAndel stillingsprosent;

    public DelvisTilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom, LocalDate tilrettelagtArbeidFom, ProsentAndel stillingsprosent) {
        super(arbeidsforhold, behovForTilretteleggingFom);
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
