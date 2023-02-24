package no.nav.foreldrepenger.common.domain.felles.opptjening;

import com.neovisionaries.i18n.CountryCode;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

public record EgenNæring(@NotNull CountryCode registrertILand,
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
}
