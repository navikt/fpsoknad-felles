package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static no.nav.foreldrepenger.common.domain.felles.TestUtils.opphold;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging;

class SvangerskapspengerTest {

    @Test
    void tidligstDatoForSvpUttakBasererSegPåBehovForTilrettelegginsdatoen() {
        var tidligstDato = LocalDate.now().minusMonths(2);
        var tilrettelegging = List.of(
                new HelTilrettelegging(null, tidligstDato.plusMonths(1), tidligstDato.plusMonths(1), List.of()),
                new DelvisTilrettelegging(null, tidligstDato, tidligstDato, ProsentAndel.valueOf(10), List.of())
        );
        var svp = new Svangerskapspenger(
                tidligstDato.plusMonths(5),
                null,
                opphold(),
                null,
                tilrettelegging
        );
        assertThat(svp.getTidligstDatoForTilrettelegging()).isEqualTo(tidligstDato);
    }
}
