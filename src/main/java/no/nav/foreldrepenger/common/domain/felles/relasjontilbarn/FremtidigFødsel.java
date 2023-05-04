package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;
import no.nav.foreldrepenger.common.domain.validation.annotations.Termindato;

public final class FremtidigFødsel extends RelasjonTilBarn {

    @Termindato
    private final LocalDate terminDato;
    @PastOrToday(nullable = true)
    private final LocalDate utstedtDato;

    @JsonCreator
    public FremtidigFødsel(int antallBarn, LocalDate terminDato, LocalDate utstedtDato, List<VedleggReferanse> vedlegg) {
        super(antallBarn, vedlegg);
        this.terminDato = terminDato;
        this.utstedtDato = utstedtDato;
    }

    public FremtidigFødsel(LocalDate terminDato, LocalDate utstedtDato) {
        this(1, terminDato, utstedtDato, emptyList());
    }

    public LocalDate getTerminDato() {
        return terminDato;
    }

    public LocalDate getUtstedtDato() {
        return utstedtDato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return terminDato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        FremtidigFødsel that = (FremtidigFødsel) o;
        return Objects.equals(terminDato, that.terminDato) && Objects.equals(utstedtDato, that.utstedtDato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), terminDato, utstedtDato);
    }

    @Override
    public String toString() {
        return "FremtidigFødsel{" + "terminDato=" + terminDato + ", utstedtDato=" + utstedtDato + "} " + super.toString();
    }
}
