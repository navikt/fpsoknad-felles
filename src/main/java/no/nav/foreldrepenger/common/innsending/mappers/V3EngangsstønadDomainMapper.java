package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.innsending.mappers.MapperEgenskaper.ENGANGSSTØNAD;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.medlemsskapFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.målformFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.søkerFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.vedleggFra;

import jakarta.xml.bind.JAXBElement;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.engangsstønad.Engangsstønad;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.innsending.mappers.jaxb.ESV3JAXBUtil;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Foedsel;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.SoekersRelasjonTilBarnet;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Termin;
import no.nav.vedtak.felles.xml.soeknad.v3.OmYtelse;
import no.nav.vedtak.felles.xml.soeknad.v3.Soeknad;

public class V3EngangsstønadDomainMapper implements DomainMapper {
    private static final ESV3JAXBUtil JAXB = new ESV3JAXBUtil();
    private static final no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.ObjectFactory ES_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory SØKNAD_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory();

    private final AktørIdTilFnrConverter aktørIdTilFnrConverter;

    public V3EngangsstønadDomainMapper(AktørIdTilFnrConverter aktørIdTilFnrConverter) {
        this.aktørIdTilFnrConverter = aktørIdTilFnrConverter;
    }

    @Override
    public MapperEgenskaper mapperEgenskaper() {
        return ENGANGSSTØNAD;
    }

    @Override
    public String tilXML(Søknad søknad, AktørId søker, SøknadEgenskap egenskap) {
        return JAXB.marshal(SØKNAD_FACTORY_V3.createSoeknad(tilModell(søknad, søker)));
    }

    @Override
    public String tilXML(Endringssøknad endringssøknad, AktørId søker, SøknadEgenskap egenskap) {
        throw new UnsupportedOperationException("Endringssøknad ikke støttet");
    }

    private Soeknad tilModell(Søknad søknad, AktørId søker) {
        var soeknad = new Soeknad();
        soeknad.setSprakvalg(målformFra(søknad.getSøker()));
        soeknad.getPaakrevdeVedlegg().addAll(vedleggFra(søknad.getVedlegg()));
        soeknad.setSoeker(søkerFra(søker, søknad.getSøker()));
        soeknad.setMottattDato(søknad.getMottattdato());
        soeknad.setTilleggsopplysninger(søknad.getTilleggsopplysninger());
        soeknad.setOmYtelse(engangsstønadFra(søknad));
        return soeknad;
    }

    private OmYtelse engangsstønadFra(Søknad søknad) {
        var omYtelse = new OmYtelse();
        omYtelse.getAny().add(JAXB.marshalToElement(engangsstønadFra((Engangsstønad) søknad.getYtelse())));
        return omYtelse;
    }

    private JAXBElement<no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.Engangsstønad> engangsstønadFra(Engangsstønad es) {
        var engangsstønad = new no.nav.vedtak.felles.xml.soeknad.engangsstoenad.v3.Engangsstønad();
        engangsstønad.setMedlemskap(medlemsskapFra(es.medlemsskap(), es.relasjonTilBarn().relasjonsDato()));
        engangsstønad.setSoekersRelasjonTilBarnet(relasjonFra(es.relasjonTilBarn()));
        return ES_FACTORY_V3.createEngangsstønad(engangsstønad);
    }

    private static SoekersRelasjonTilBarnet relasjonFra(RelasjonTilBarn relasjon) {
        if (relasjon instanceof FremtidigFødsel f) {
            return create(f);
        }
        if (relasjon instanceof Fødsel f) {
            return create(f);
        }
        if (relasjon instanceof Adopsjon a) {
            return create(a);
        }
        throw new UnexpectedInputException("Relasjon %s er ikke støttet", relasjon.getClass().getSimpleName());
    }

    private static SoekersRelasjonTilBarnet create(Adopsjon adopsjon) {
        var adopsjonXML = new no.nav.vedtak.felles.xml.soeknad.felles.v3.Adopsjon();
        adopsjonXML.setAntallBarn(adopsjon.getAntallBarn());
        adopsjonXML.getFoedselsdato().addAll(adopsjon.getFødselsdato());
        adopsjonXML.setOmsorgsovertakelsesdato(adopsjon.getOmsorgsovertakelsesdato());
        adopsjonXML.setAdopsjonAvEktefellesBarn(adopsjon.isEktefellesBarn());
        adopsjonXML.setAnkomstdato(adopsjon.getAnkomstDato());
        return adopsjonXML;
    }

    private static SoekersRelasjonTilBarnet create(Fødsel fødsel) {
        var foedsel = new Foedsel();
        foedsel.setFoedselsdato(fødsel.getFødselsdato().get(0));
        foedsel.setAntallBarn(fødsel.getAntallBarn());
        return foedsel;
    }

    private static SoekersRelasjonTilBarnet create(FremtidigFødsel termin) {
        var terminXML = new Termin();
        terminXML.setTermindato(termin.getTerminDato());
        terminXML.setUtstedtdato(termin.getUtstedtDato());
        terminXML.setAntallBarn(termin.getAntallBarn());
        return terminXML;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [oppslag=" + aktørIdTilFnrConverter + ", mapperEgenskaper=" + mapperEgenskaper() + "]";
    }
}
