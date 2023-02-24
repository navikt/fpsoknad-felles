package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.validation.annotations.BarnOgFødselsdatoer;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

@BarnOgFødselsdatoer
public final class Fødsel extends RelasjonTilBarn {

    private final List<@PastOrToday(message = "Fødselsdato for barn [${validatedValue}] kan ikke være en en framtidig dato") LocalDate> fødselsdato;
    private final LocalDate termindato;

    public Fødsel(int antallBarn, List<LocalDate> fødselsdato, LocalDate termindato, List<VedleggReferanse> vedlegg) {
        super(antallBarn, vedlegg);
        this.fødselsdato = fødselsdato;
        this.termindato = termindato;
    }

    public List<LocalDate> getFødselsdato() {
        return fødselsdato;
    }

    public LocalDate getTermindato() {
        return termindato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return safeStream(fødselsdato)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Fødselsdato for barnet må være satt!"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Fødsel fødsel = (Fødsel) o;
        return Objects.equals(fødselsdato, fødsel.fødselsdato) && Objects.equals(termindato, fødsel.termindato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fødselsdato, termindato);
    }

    @Override
    public String toString() {
        return "Fødsel{" + "fødselsdato=" + fødselsdato + ", termindato=" + termindato + "} " + super.toString();
    }
}
