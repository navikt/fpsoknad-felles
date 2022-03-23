package no.nav.foreldrepenger.common.innsyn.v2;

import java.time.LocalDate;

record Familiehendelse(LocalDate fødselsdato,
                       LocalDate termindato,
                       int antallBarn,
                       LocalDate omsorgsovertakelse) {
}
