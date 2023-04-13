package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class Adopsjon extends RelasjonTilBarn {

    @NotNull(message = "{ytelse.relasjontilbarn.adopsjon.omsorggsovertakelsesdato.notnull}")
    private final LocalDate omsorgsovertakelsesdato;
    private final boolean ektefellesBarn;
    private final boolean søkerAdopsjonAlene;
    private final LocalDate ankomstDato;
    private final List<@PastOrToday(message = "{ytelse.relasjontilbarn.adopsjon.fødselssdato.framtid}") LocalDate> fødselsdato;

    public Adopsjon(int antallBarn, LocalDate omsorgsovertakelsesdato, boolean ektefellesBarn, boolean søkerAdopsjonAlene,
                    List<VedleggReferanse> vedlegg, LocalDate ankomstDato, List<LocalDate> fødselsdato) {
        super(antallBarn, vedlegg);
        this.omsorgsovertakelsesdato = omsorgsovertakelsesdato;
        this.ektefellesBarn = ektefellesBarn;
        this.ankomstDato = ankomstDato;
        this.fødselsdato = fødselsdato;
        this.søkerAdopsjonAlene = søkerAdopsjonAlene;
    }

    public LocalDate getOmsorgsovertakelsesdato() {
        return omsorgsovertakelsesdato;
    }

    public boolean isEktefellesBarn() {
        return ektefellesBarn;
    }

    public boolean isSøkerAdopsjonAlene() {
        return søkerAdopsjonAlene;
    }

    public LocalDate getAnkomstDato() {
        return ankomstDato;
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
        Adopsjon adopsjon = (Adopsjon) o;
        return ektefellesBarn == adopsjon.ektefellesBarn && søkerAdopsjonAlene == adopsjon.søkerAdopsjonAlene && Objects.equals(
                omsorgsovertakelsesdato, adopsjon.omsorgsovertakelsesdato) && Objects.equals(ankomstDato, adopsjon.ankomstDato)
                && Objects.equals(fødselsdato, adopsjon.fødselsdato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), omsorgsovertakelsesdato, ektefellesBarn, søkerAdopsjonAlene, ankomstDato, fødselsdato);
    }

    @Override
    public String toString() {
        return "Adopsjon{" +
                "omsorgsovertakelsesdato=" + omsorgsovertakelsesdato +
                ", ektefellesBarn=" + ektefellesBarn +
                ", søkerAdopsjonAlene=" + søkerAdopsjonAlene +
                ", ankomstDato=" + ankomstDato +
                ", fødselsdato=" + fødselsdato +
                "} " + super.toString();
    }
}
