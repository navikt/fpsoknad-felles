package no.nav.foreldrepenger.common.innsyn.svp;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record Søknad(@NotNull Set<Arbeidsforhold> arbeidsforhold) {
}
