package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;

import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = Fødsel.class, name = "fødsel"),
        @Type(value = Adopsjon.class, name = "adopsjon"),
        @Type(value = FremtidigFødsel.class, name = "termin"),
        @Type(value = Omsorgsovertakelse.class, name = "omsorgsovertakelse")
})
public abstract sealed class RelasjonTilBarn permits Fødsel,FremtidigFødsel,Adopsjon,Omsorgsovertakelse {

    public abstract LocalDate relasjonsDato();

    private final List<VedleggReferanse> vedlegg;
    @Positive
    private final int antallBarn;

    protected RelasjonTilBarn(int antallBarn, List<VedleggReferanse> vedlegg) {
        this.antallBarn = antallBarn;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }

    public List<VedleggReferanse> getVedlegg() {
        return vedlegg;
    }

    public int getAntallBarn() {
        return antallBarn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RelasjonTilBarn that = (RelasjonTilBarn) o;
        return antallBarn == that.antallBarn && Objects.equals(vedlegg, that.vedlegg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vedlegg, antallBarn);
    }

    @Override
    public String toString() {
        return "RelasjonTilBarn{" + "vedlegg=" + vedlegg + ", antallBarn=" + antallBarn + '}';
    }
}
