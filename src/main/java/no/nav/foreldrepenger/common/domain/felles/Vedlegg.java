package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.LASTET_OPP;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.SEND_SENERE;
import static no.nav.foreldrepenger.common.util.StringUtil.limit;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = { "vedlegg" })
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = ValgfrittVedlegg.class, name = "valgfritt"),
        @Type(value = PåkrevdVedlegg.class, name = "påkrevd")
})
public abstract class Vedlegg {

    private static final Logger LOG = LoggerFactory.getLogger(Vedlegg.class);

    @Valid
    private final VedleggMetaData metadata;
    private final byte[] vedlegg;

    @JsonCreator
    protected Vedlegg(VedleggMetaData metadata, byte[] vedlegg) {
        this.metadata = metadata;
        this.vedlegg = vedlegg;
    }

    @JsonIgnore
    public String getBeskrivelse() {
        return Optional.ofNullable(metadata.beskrivelse())
                .orElse(getDokumentType().getBeskrivelse());
    }

    @JsonIgnore
    public InnsendingsType getInnsendingsType() {
        InnsendingsType type = metadata.innsendingsType();
        if (getStørrelse() == 0) {
            if (!SEND_SENERE.equals(type) && type != null) {
                LOG.warn("Feil innsendingstype {} for {}, ingen vedlegg, setter type til SEND_SENERE", type,
                        metadata.dokumentType());
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
        return metadata.id();
    }

    @JsonIgnore
    public DokumentType getDokumentType() {
        return metadata.dokumentType();
    }

    @JsonIgnore
    public long getStørrelse() {
        return Optional.ofNullable(vedlegg)
                .map(v -> v.length)
                .orElse(0);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "metadata=" + metadata + "vedlegg=" + limit(vedlegg, 50);
    }
}
