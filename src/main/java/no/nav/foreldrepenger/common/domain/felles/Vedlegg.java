package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.LASTET_OPP;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.SEND_SENERE;
import static no.nav.foreldrepenger.common.util.StringUtil.limit;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.Valid;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = ValgfrittVedlegg.class, name = "valgfritt"),
        @Type(value = PåkrevdVedlegg.class, name = "påkrevd")
})
public abstract class Vedlegg {

    private static final Logger LOG = LoggerFactory.getLogger(Vedlegg.class);

    @Valid
    private final VedleggMetaData metadata;
    private byte[] innhold;

    @JsonCreator
    protected Vedlegg(VedleggMetaData metadata) {
        this.metadata = metadata;
    }

    public VedleggMetaData getMetadata() {
        return metadata;
    }

    public byte[] getInnhold() {
        return innhold;
    }

    public void setInnhold(byte[] innhold) {
        this.innhold = innhold;
    }

    @JsonIgnore
    public String getBeskrivelse() {
        return Optional.ofNullable(metadata.beskrivelse())
                .orElse(getDokumentType().getTittel());
    }

    @JsonIgnore
    public InnsendingsType getInnsendingsType() {
        var type = metadata.innsendingsType();
        if (getStørrelse() == 0) {
            if (!SEND_SENERE.equals(type) && type != null) {
                LOG.warn("Feil innsendingstype {} for {}, ingen vedlegg, setter type til SEND_SENERE", type, metadata.dokumentType());
            }
            return SEND_SENERE;
        }
        if (type == null) {
            LOG.info("Innsendingstype for {} er ikke satt, setter til {}, siden vi har vedlegg med størrelse {}",
                    metadata.dokumentType(), LASTET_OPP, getStørrelse());
            return LASTET_OPP;
        }
        return type;
    }

    @JsonIgnore
    public String getId() {
        return metadata.id().referanse();
    }

    @JsonIgnore
    public DokumentType getDokumentType() {
        return metadata.dokumentType();
    }

    @JsonIgnore
    public long getStørrelse() {
        return Optional.ofNullable(innhold)
                .map(v -> v.length)
                .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vedlegg vedlegg = (Vedlegg) o;
        return Objects.equals(metadata, vedlegg.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "metadata=" + metadata + "vedlegg=" + limit(innhold, 50);
    }
}
