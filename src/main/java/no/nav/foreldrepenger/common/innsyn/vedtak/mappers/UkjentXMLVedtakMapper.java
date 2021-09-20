package no.nav.foreldrepenger.common.innsyn.vedtak.mappers;

import static no.nav.foreldrepenger.common.util.StringUtil.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nav.foreldrepenger.common.innsending.mappers.MapperEgenskaper;
import no.nav.foreldrepenger.common.innsyn.SøknadEgenskap;
import no.nav.foreldrepenger.common.innsyn.vedtak.Vedtak;

public class UkjentXMLVedtakMapper implements XMLVedtakMapper {

    private static final Logger LOG = LoggerFactory.getLogger(UkjentXMLVedtakMapper.class);

    @Override
    public Vedtak tilVedtak(String xml, SøknadEgenskap egenskap) {
        LOG.info("Kan ikke mappe {}", limit(xml));
        return null;
    }

    @Override
    public MapperEgenskaper mapperEgenskaper() {
        return MapperEgenskaper.UKJENT;
    }
}
