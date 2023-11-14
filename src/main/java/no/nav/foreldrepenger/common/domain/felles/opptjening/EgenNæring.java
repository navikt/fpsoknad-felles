package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record EgenNæring(CountryCode registrertILand,
                         @Valid Orgnummer orgNummer,
                         @Length(max = 100) @Pattern(regexp = FRITEKST) String orgName,
                         @Valid List<Virksomhetstype> virksomhetsTyper,
                         @Valid ÅpenPeriode periode,
                         boolean nærRelasjon,
                         @Valid List<Regnskapsfører> regnskapsførere,
                         boolean erNyOpprettet,
                         boolean erVarigEndring,
                         boolean erNyIArbeidslivet,
                         long næringsinntektBrutto,
                         LocalDate endringsDato,
                         LocalDate oppstartsDato,
                         @Length(max = 1000) @Pattern(regexp = FRITEKST) String beskrivelseEndring,
                         @Valid ProsentAndel stillingsprosent) {

    @JsonCreator
    public EgenNæring {
        virksomhetsTyper = Optional.ofNullable(virksomhetsTyper).orElse(emptyList());
        regnskapsførere = Optional.ofNullable(regnskapsførere).orElse(emptyList());
    }
}
