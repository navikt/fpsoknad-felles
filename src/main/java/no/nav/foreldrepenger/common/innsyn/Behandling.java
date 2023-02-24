package no.nav.foreldrepenger.common.innsyn;

import no.nav.foreldrepenger.common.domain.felles.BehandlingTema;

import java.time.LocalDateTime;
import java.util.List;

public record Behandling(LocalDateTime opprettetTidspunkt,
                         LocalDateTime endretTidspunkt,
                         BehandlingStatus status,
                         BehandlingType type,
                         BehandlingTema tema,
                         String behandlendeEnhet,
                         String behandlendeEnhetNavn,
                         BehandlingResultat behandlingResultat,
                         List<String> inntektsmeldinger,
                         InnsynsSøknad søknad) {


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime opprettetTidspunkt;
        private LocalDateTime endretTidspunkt;
        private BehandlingStatus status;
        private BehandlingType type;
        private BehandlingTema tema;
        private String behandlendeEnhet;
        private String behandlendeEnhetNavn;
        private BehandlingResultat behandlingResultat;
        private List<String> inntektsmeldinger;
        private InnsynsSøknad søknad;

        Builder() {
        }

        public Builder opprettetTidspunkt(LocalDateTime opprettetTidspunkt) {
            this.opprettetTidspunkt = opprettetTidspunkt;
            return this;
        }

        public Builder endretTidspunkt(LocalDateTime endretTidspunkt) {
            this.endretTidspunkt = endretTidspunkt;
            return this;
        }

        public Builder status(BehandlingStatus status) {
            this.status = status;
            return this;
        }

        public Builder type(BehandlingType type) {
            this.type = type;
            return this;
        }

        public Builder tema(BehandlingTema tema) {
            this.tema = tema;
            return this;
        }

        public Builder behandlendeEnhet(String behandlendeEnhet) {
            this.behandlendeEnhet = behandlendeEnhet;
            return this;
        }

        public Builder behandlendeEnhetNavn(String behandlendeEnhetNavn) {
            this.behandlendeEnhetNavn = behandlendeEnhetNavn;
            return this;
        }

        public Builder behandlingResultat(BehandlingResultat behandlingResultat) {
            this.behandlingResultat = behandlingResultat;
            return this;
        }

        public Builder inntektsmeldinger(List<String> inntektsmeldinger) {
            this.inntektsmeldinger = inntektsmeldinger;
            return this;
        }

        public Builder søknad(InnsynsSøknad søknad) {
            this.søknad = søknad;
            return this;
        }

        public Behandling build() {
            return new Behandling(this.opprettetTidspunkt, this.endretTidspunkt, this.status, this.type, this.tema,
                    this.behandlendeEnhet, this.behandlendeEnhetNavn, this.behandlingResultat, this.inntektsmeldinger, this.søknad);
        }
    }

}
