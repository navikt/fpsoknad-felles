package no.nav.foreldrepenger.common.domain.felles.opptjening;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

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
                         @Valid ProsentAndel stillingsprosent,
                         @Valid List<VedleggReferanse> vedlegg) {

    @JsonCreator
    public EgenNæring {
        virksomhetsTyper = Optional.ofNullable(virksomhetsTyper).orElse(emptyList());
        regnskapsførere = Optional.ofNullable(regnskapsførere).orElse(emptyList());
        vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}
