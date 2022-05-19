package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

@Getter
@ToString(exclude = "vedlegg")
@EqualsAndHashCode(exclude = "vedlegg")
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = NorskOrganisasjon.class, name = "norsk"),
        @Type(value = UtenlandskOrganisasjon.class, name = "utenlandsk")
})
public abstract sealed class EgenNæring permits NorskOrganisasjon,UtenlandskOrganisasjon {
    private final List<Virksomhetstype> virksomhetsTyper;
    @Valid
    private final ÅpenPeriode periode;
    private final boolean nærRelasjon;
    @Valid
    private final List<Regnskapsfører> regnskapsførere;
    private final boolean erNyOpprettet;
    private final boolean erVarigEndring;
    private final boolean erNyIArbeidslivet;
    private final long næringsinntektBrutto;
    private final LocalDate endringsDato;
    private final LocalDate oppstartsDato;
    @Length(max = 1000)
    @Pattern(regexp = FRITEKST)
    private final String beskrivelseEndring;
    @Valid
    private final ProsentAndel stillingsprosent;
    private final List<@Pattern(regexp = FRITEKST) String> vedlegg;

    @JsonCreator
    protected EgenNæring(List<Virksomhetstype> virksomhetsTyper,
                      ÅpenPeriode periode,
                      boolean nærRelasjon,
                      List<Regnskapsfører> regnskapsførere,
                      boolean erNyOpprettet,
                      boolean erVarigEndring,
                      boolean erNyIArbeidslivet,
                      long næringsinntektBrutto,
                      LocalDate endringsDato,
                      LocalDate oppstartsDato,
                      String beskrivelseEndring,
                      ProsentAndel stillingsprosent,
                      List<String> vedlegg) {
        this.virksomhetsTyper = virksomhetsTyper;
        this.periode = periode;
        this.nærRelasjon = nærRelasjon;
        this.regnskapsførere = regnskapsførere;
        this.erNyOpprettet = erNyOpprettet;
        this.erVarigEndring = erVarigEndring;
        this.erNyIArbeidslivet = erNyIArbeidslivet;
        this.næringsinntektBrutto = næringsinntektBrutto;
        this.endringsDato = endringsDato;
        this.oppstartsDato = oppstartsDato;
        this.beskrivelseEndring = beskrivelseEndring;
        this.stillingsprosent = stillingsprosent;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}
