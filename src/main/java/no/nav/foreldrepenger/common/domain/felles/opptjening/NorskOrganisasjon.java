package no.nav.foreldrepenger.common.domain.felles.opptjening;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

@Value
@Valid
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NorskOrganisasjon extends EgenNæring {

    Orgnummer orgNummer;
    @Length(max = 100)
    String orgName;

    @Builder
    private NorskOrganisasjon(List<Virksomhetstype> virksomhetsTyper, ÅpenPeriode periode,
            boolean nærRelasjon, List<Regnskapsfører> regnskapsførere, boolean erNyOpprettet, boolean erVarigEndring,
            boolean erNyIArbeidslivet, long næringsinntektBrutto, LocalDate endringsDato, LocalDate oppstartsDato,
            String beskrivelseEndring, ProsentAndel stillingsprosent,
            List<String> vedlegg,
            Orgnummer orgNummer, String orgName) {
        super(virksomhetsTyper, periode, nærRelasjon, regnskapsførere, erNyOpprettet,
                erVarigEndring, erNyIArbeidslivet,
                næringsinntektBrutto, endringsDato, oppstartsDato, beskrivelseEndring, stillingsprosent, vedlegg);
        this.orgName = orgName;
        this.orgNummer = orgNummer;
    }
}
