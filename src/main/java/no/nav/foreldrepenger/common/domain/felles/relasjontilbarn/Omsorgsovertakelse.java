package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

public final class Omsorgsovertakelse extends RelasjonTilBarn {

    private final LocalDate omsorgsovertakelsesdato;

    @NotNull
    @Size(min = 1, message = "Fødselsdato ved omsorgsovertagelse må inneholde minst {min} fødselsdato. Nå ble {value} sendt inn.")
    private final List<@PastOrToday(message = "Fødselsdato for barn [${validatedValue}] kan ikke være en en framtidig dato") LocalDate> fødselsdato;

    @AssertTrue(message = "Ved omsorgsovertagelse må antall barn match antall fødselsdatoer oppgitt!")
    private boolean isAntallBarnSkalLikAntallFødselsdatoerVedOmsorgsovertakelse() {
        if (fødselsdato == null) {
            return false;
        }
        return fødselsdato.size() == getAntallBarn();
    }

    @JsonCreator
    public Omsorgsovertakelse(int antallBarn, LocalDate omsorgsovertakelsesdato, List<LocalDate> fødselsdato, List<VedleggReferanse> vedlegg) {
        super(antallBarn, vedlegg);
        this.omsorgsovertakelsesdato = omsorgsovertakelsesdato;
        this.fødselsdato = fødselsdato;
    }

    public Omsorgsovertakelse(LocalDate omsorgsovertakelsesdato, LocalDate fødselsdato) {
        this(1, omsorgsovertakelsesdato, singletonList(fødselsdato), emptyList());
    }


    public LocalDate getOmsorgsovertakelsesdato() {
        return omsorgsovertakelsesdato;
    }

    public List<LocalDate> getFødselsdato() {
        return fødselsdato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return omsorgsovertakelsesdato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Omsorgsovertakelse that = (Omsorgsovertakelse) o;
        return Objects.equals(omsorgsovertakelsesdato, that.omsorgsovertakelsesdato) && Objects.equals(fødselsdato, that.fødselsdato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), omsorgsovertakelsesdato, fødselsdato);
    }

    @Override
    public String toString() {
        return "Omsorgsovertakelse{" + "omsorgsovertakelsesdato=" + omsorgsovertakelsesdato + ", fødselsdato=" + fødselsdato + "} " + super.toString();
    }
}
