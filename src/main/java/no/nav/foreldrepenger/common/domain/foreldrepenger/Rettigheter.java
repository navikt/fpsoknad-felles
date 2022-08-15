package no.nav.foreldrepenger.common.domain.foreldrepenger;

import no.nav.foreldrepenger.common.domain.validation.annotations.Rettighet;

@Rettighet
public record Rettigheter(boolean harAnnenForelderRett,
                          boolean harAleneOmsorgForBarnet,
                          boolean harMorUføretrygd,
                          boolean harMorRettPåForeldrepengerIEtEØSLand) {
}
