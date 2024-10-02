package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static no.nav.foreldrepenger.common.domain.felles.TestUtils.opphold;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.delTilrettelegging;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.helTilrettelegging;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilretteleggingsbehov.Tilretteleggingbehov;

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
                tilrettelegging,
                null,
                List.of()
        );
        assertThat(svp.getTidligstDatoForTilrettelegging()).isEqualTo(tidligstDato);
    }

    @Test
    void tidligstDatoForSvpUttakBasererSegPåBehovForTilrettelegginsdatoenNy() {
        var tidligstDato = LocalDate.now().minusMonths(2);
        var tilrettelegging = List.of(helTilrettelegging(), delTilrettelegging());
        var tilretteleggingsbehov = List.of(
                new Tilretteleggingbehov(null, tidligstDato.plusMonths(2), tilrettelegging, null),
                new Tilretteleggingbehov(null, tidligstDato, tilrettelegging, null)
        );
        var svp = new Svangerskapspenger(
                tidligstDato.plusMonths(5),
                null,
                opphold(),
                null,
                null,
                tilretteleggingsbehov,
                List.of()
        );
        assertThat(svp.getTidligstDatoForTilrettelegging()).isEqualTo(tidligstDato);
    }
}
