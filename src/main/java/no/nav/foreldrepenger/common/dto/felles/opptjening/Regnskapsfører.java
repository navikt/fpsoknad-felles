package no.nav.foreldrepenger.common.dto.felles.opptjening;

import org.hibernate.validator.constraints.Length;

public record Regnskapsfører(@Length(max = 100) String navn, String telefon) {
}
