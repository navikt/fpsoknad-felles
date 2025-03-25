package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Familiehendelse(LocalDate fødselsdato,
                              LocalDate termindato,
                              @NotNull int antallBarn,
                              LocalDate omsorgsovertakelse) {
}
