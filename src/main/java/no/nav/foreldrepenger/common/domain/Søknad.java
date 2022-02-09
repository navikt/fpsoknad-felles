package no.nav.foreldrepenger.common.domain;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nav.foreldrepenger.common.domain.felles.PåkrevdVedlegg;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = "mottattdato")
@JsonPropertyOrder({ "mottattdato", "søker", "ytelse", "begrunnelseForSenSøknad", "tilleggsopplysninger", "vedlegg" })
public class Søknad {

    @NotNull
    private LocalDate mottattdato;
    @Valid
    private final Søker søker;
    @Valid
    private final Ytelse ytelse;
    @Length(max = 2000)
    @Pattern(regexp = FRITEKST)
    private String begrunnelseForSenSøknad;
    @Length(max = 4000)
    @Pattern(regexp = FRITEKST)
    private String tilleggsopplysninger;
    @Valid
    private final List<Vedlegg> vedlegg;

    public Søknad(LocalDate mottattdato, Søker søker, Ytelse ytelse, Vedlegg... vedlegg) {
        this(mottattdato, søker, ytelse, asList(vedlegg));
    }

    @JsonCreator
    public Søknad(@JsonProperty("mottattdato") LocalDate mottattdato, @JsonProperty("søker") Søker søker,
            @JsonProperty("ytelse") Ytelse ytelse,
            @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        this.mottattdato = mottattdato;
        this.søker = søker;
        this.ytelse = ytelse;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
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

    @JsonIgnore
    public BrukerRolle getSøknadsRolle() {
        return søker.getSøknadsRolle();
    }

    @JsonIgnore
    public LocalDate getFørsteUttaksdag() {
        if (ytelse instanceof Foreldrepenger fp) {
            return fp.getFordeling().getFørsteUttaksdag();
        }
        return null;
    }

    @JsonIgnore
    public LocalDate getFørsteInntektsmeldingDag() {
        return Optional.ofNullable(getFørsteUttaksdag())
                .map(d -> d.minusWeeks(4))
                .orElse(null);
    }
}
