package no.nav.foreldrepenger.common.innsyn.uttaksplan;

import java.time.LocalDate;

import no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad;

public record SøknadsGrunnlagDto(LocalDate termindato,
                                 LocalDate fødselsdato,
                                 LocalDate omsorgsovertakelsesdato,
                                 Dekningsgrad dekningsgrad,
                                 Integer antallBarn,
                                 Boolean søkerErFarEllerMedmor,
                                 Boolean morErAleneOmOmsorg,
                                 Boolean morHarRett,
                                 Boolean morErUfør,
                                 Boolean morMottarForeldrepengerIEØS,
                                 Boolean farMedmorErAleneOmOmsorg,
                                 Boolean farMedmorHarRett,
                                 Boolean annenForelderErInformert) {
}
