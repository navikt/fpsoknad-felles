package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

public final class Fødsel extends RelasjonTilBarn {

    @NotNull
    @Size(min = 1, message = "Fødselsdato ved fødsel må inneholde minst {min} fødselsdato. Nå ble {value} fødselsdatoer sendt inn.")
    private final List<@PastOrToday(message = "Fødselsdato for barn [${validatedValue}] kan ikke være en en framtidig dato") LocalDate> fødselsdato;

    private final LocalDate termindato;

    public Fødsel(int antallBarn, List<LocalDate> fødselsdato, LocalDate termindato) {
        super(antallBarn);
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
