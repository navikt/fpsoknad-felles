package no.nav.foreldrepenger.common.domain.felles;

import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000027;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000050;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000067;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000119;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000122;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DokumentTypeTest {

    @Test
    void fraTittelTilVedlegg() {
        var dokumentType = DokumentType.fraTittel(I000122.getTittel());
        assertThat(dokumentType).isEqualTo(I000122);
        assertThat(dokumentType.erVedlegg()).isTrue();
        assertThat(dokumentType.erFørstegangssøknad()).isFalse();
        assertThat(dokumentType.erEndringssøknad()).isFalse();
        assertThat(dokumentType.erInntektsmelding()).isFalse();
        assertThat(dokumentType.erUttalelseOmTilbakekreving()).isFalse();
        assertThat(dokumentType.erKlage()).isFalse();
    }

    @Test
    void fraTittelTilTilbakekrevingsuttalelse() {
        var dokumentType = DokumentType.fraTittel(I000119.getTittel());
        assertThat(dokumentType).isEqualTo(I000119);
        assertThat(dokumentType.erVedlegg()).isFalse();
        assertThat(dokumentType.erFørstegangssøknad()).isFalse();
        assertThat(dokumentType.erEndringssøknad()).isFalse();
        assertThat(dokumentType.erInntektsmelding()).isFalse();
        assertThat(dokumentType.erUttalelseOmTilbakekreving()).isTrue();
        assertThat(dokumentType.erKlage()).isFalse();
    }

    @Test
    void fraTittelTilEndringssøknad() {
        var dokumentType = DokumentType.fraTittel(I000050.getTittel());
        assertThat(dokumentType).isEqualTo(I000050);
        assertThat(dokumentType.erVedlegg()).isFalse();
        assertThat(dokumentType.erFørstegangssøknad()).isFalse();
        assertThat(dokumentType.erEndringssøknad()).isTrue();
        assertThat(dokumentType.erInntektsmelding()).isFalse();
        assertThat(dokumentType.erUttalelseOmTilbakekreving()).isFalse();
        assertThat(dokumentType.erKlage()).isFalse();
    }

    @Test
    void fraTittelTilIM() {
        var dokumentType = DokumentType.fraTittel(I000067.getTittel());
        assertThat(dokumentType).isEqualTo(I000067);
        assertThat(dokumentType.erVedlegg()).isFalse();
        assertThat(dokumentType.erFørstegangssøknad()).isFalse();
        assertThat(dokumentType.erEndringssøknad()).isFalse();
        assertThat(dokumentType.erInntektsmelding()).isTrue();
        assertThat(dokumentType.erUttalelseOmTilbakekreving()).isFalse();
        assertThat(dokumentType.erKlage()).isFalse();
    }

    @Test
    void fraTittelTilKlage() {
        var dokumentType = DokumentType.fraTittel(I000027.getTittel());
        var dokumentTypeKlage = DokumentType.fraTittel("Klage");
        var dokumentTypeAnke = DokumentType.fraTittel("Anke");
        assertThat(dokumentType).isEqualTo(dokumentTypeKlage).isEqualTo(dokumentTypeAnke).isEqualTo(I000027);
        assertThat(dokumentType.erVedlegg()).isFalse();
        assertThat(dokumentType.erFørstegangssøknad()).isFalse();
        assertThat(dokumentType.erEndringssøknad()).isFalse();
        assertThat(dokumentType.erInntektsmelding()).isFalse();
        assertThat(dokumentType.erUttalelseOmTilbakekreving()).isFalse();
        assertThat(dokumentType.erKlage()).isTrue();
    }
}
