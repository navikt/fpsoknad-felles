package no.nav.foreldrepenger.common.domain;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.felles.PåkrevdVedlegg;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;

@JsonPropertyOrder({ "mottattdato", "søker", "ytelse", "begrunnelseForSenSøknad", "tilleggsopplysninger", "vedlegg" })
public class Søknad {

    @NotNull
    private final LocalDate mottattdato;
    @Valid
    private final Søker søker;
    @Valid
    private final Ytelse ytelse;
    @Length(max = 4000)
    @Pattern(regexp = FRITEKST)
    private final String tilleggsopplysninger;
    @Valid
    private final List<Vedlegg> vedlegg;


    @JsonCreator
    public Søknad(LocalDate mottattdato, Søker søker, Ytelse ytelse, String tilleggsopplysninger, List<Vedlegg> vedlegg) {
        this.mottattdato = mottattdato;
        this.søker = søker;
        this.ytelse = ytelse;
        this.tilleggsopplysninger = tilleggsopplysninger;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }

    public LocalDate getMottattdato() {
        return mottattdato;
    }

    public Søker getSøker() {
        return søker;
    }

    public Ytelse getYtelse() {
        return ytelse;
    }

    public String getTilleggsopplysninger() {
        return tilleggsopplysninger;
    }

    public List<Vedlegg> getVedlegg() {
        return vedlegg;
    }

    @JsonIgnore
    public List<PåkrevdVedlegg> getPåkrevdeVedlegg() {
        return safeStream(vedlegg)
                .filter(PåkrevdVedlegg.class::isInstance)
                .map(PåkrevdVedlegg.class::cast)
                .toList();
    }

    @JsonIgnore
    public List<ValgfrittVedlegg> getFrivilligeVedlegg() {
        return safeStream(vedlegg)
                .filter(ValgfrittVedlegg.class::isInstance)
                .map(ValgfrittVedlegg.class::cast)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Søknad søknad = (Søknad) o;
        return Objects.equals(mottattdato, søknad.mottattdato) && Objects.equals(søker, søknad.søker) && Objects.equals(ytelse, søknad.ytelse) && Objects.equals(tilleggsopplysninger, søknad.tilleggsopplysninger) && Objects.equals(vedlegg, søknad.vedlegg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mottattdato, søker, ytelse, tilleggsopplysninger, vedlegg);
    }

    @Override
    public String toString() {
        return "Søknad{" +
                "mottattdato=" + mottattdato +
                ", søker=" + søker +
                ", ytelse=" + ytelse +
                ", tilleggsopplysninger='" + tilleggsopplysninger + '\'' +
                ", vedlegg=" + vedlegg +
                '}';
    }
}
