package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = Fødsel.class, name = "fødsel"),
        @Type(value = Adopsjon.class, name = "adopsjon"),
        @Type(value = FremtidigFødsel.class, name = "termin"),
        @Type(value = Omsorgsovertakelse.class, name = "omsorgsovertakelse")
})
public abstract sealed class RelasjonTilBarn permits Fødsel,FremtidigFødsel,Adopsjon,Omsorgsovertakelse {

    public abstract LocalDate relasjonsDato();

    @Positive
    private final int antallBarn;

    @AssertTrue(message = "Relasjonsdato for relasjon til barnet må være satt!")
    public boolean isRelasjonsDatoSatt() {
        return relasjonsDato() != null;
    }

    protected RelasjonTilBarn(int antallBarn) {
        this.antallBarn = antallBarn;
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
        return antallBarn == that.antallBarn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(antallBarn);
    }

    @Override
    public String toString() {
        return "RelasjonTilBarn{" + " antallBarn=" + antallBarn + '}';
    }
}
