package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public final class Omsorgsovertakelse extends RelasjonTilBarn {

    private final LocalDate omsorgsovertakelsesdato;
    private final List<LocalDate> fødselsdato;

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
