package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Familiehendelse(LocalDate fødselsdato,
                              LocalDate termindato,
                              int antallBarn,
                              LocalDate omsorgsovertakelse) {
}
