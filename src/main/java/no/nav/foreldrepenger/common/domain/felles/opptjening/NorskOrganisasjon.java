package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class NorskOrganisasjon extends EgenNæring {

    @Valid
    private final Orgnummer orgNummer;
    @Length(max = 100)
    @Pattern(regexp = FRITEKST)
    private final String orgName;

    @Builder
    private NorskOrganisasjon(List<Virksomhetstype> virksomhetsTyper, ÅpenPeriode periode,
            boolean nærRelasjon, List<Regnskapsfører> regnskapsførere, boolean erNyOpprettet, boolean erVarigEndring,
            boolean erNyIArbeidslivet, long næringsinntektBrutto, LocalDate endringsDato, LocalDate oppstartsDato,
            String beskrivelseEndring, ProsentAndel stillingsprosent, List<String> vedlegg, Orgnummer orgNummer, String orgName) {
        super(virksomhetsTyper, periode, nærRelasjon, regnskapsførere, erNyOpprettet, erVarigEndring, erNyIArbeidslivet,
                næringsinntektBrutto, endringsDato, oppstartsDato, beskrivelseEndring, stillingsprosent, vedlegg);
        this.orgName = orgName;
        this.orgNummer = orgNummer;
    }

    @Override
    public String toString() {
        return "NorskOrganisasjon{" + "orgNummer=" + orgNummer + ", orgName='" + mask(orgName) + '\'' + "} " + super.toString();
    }
}
