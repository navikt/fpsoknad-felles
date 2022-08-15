package no.nav.foreldrepenger.common.domain.foreldrepenger;

import com.fasterxml.jackson.annotation.JsonAlias;

import no.nav.foreldrepenger.common.domain.validation.annotations.Rettighet;

@Rettighet
public record Rettigheter(boolean harAnnenForelderRett,
                          boolean harAleneOmsorgForBarnet,
                          boolean harMorUføretrygd,
                          @JsonAlias("harMorForeldrepengerEØS") // Fjern etter at både api og mottak er oppdatert
                          boolean harRettPåForeldrepengerIEØS) {
}
