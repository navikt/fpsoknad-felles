package no.nav.foreldrepenger.common.innsending.mappers.jaxb;

import static java.lang.String.format;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.getInputStreamFromResource;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.getURLFromResource;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import no.nav.foreldrepenger.common.error.UnexpectedInputException;

abstract class AbstractJAXBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractJAXBUtil.class);
    private final JAXBContext context;
    private final Schema schema;
    private final boolean validateMarshalling;

    AbstractJAXBUtil(JAXBContext context, boolean validateMarhsalling, String... xsds) {
        this.context = context;
        this.schema = schemaFra(xsds);
        this.validateMarshalling = validateMarhsalling;
    }

    static JAXBContext contextFra(Class<?>... classes) {
        try {
            return JAXBContext.newInstance(classes);
        } catch (JAXBException e) {
            throw new UnexpectedInputException(
                    format("Feil ved konfigurasjon av kontekst fra %s", Arrays.toString(classes)), e);
        }
    }

    public Element marshalToElement(Object model) {
        try {
            var res = new DOMResult();
            marshaller().marshal(model, res);
            return ((Document) res.getNode()).getDocumentElement();
        } catch (JAXBException e) {
            throw new UnexpectedInputException(format("Feil ved marshalling av model %s", model.getClass()), e);
        }
    }

    public String marshal(Object model) {
        try {
            var sw = new StringWriter();
            marshaller().marshal(model, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new UnexpectedInputException(format("Feil ved marshalling av model %s", model.getClass()), e);
        }
    }

    private static Schema schemaFra(String... xsds) {
        try {
            var schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            return schemaFactory.newSchema(sourcesFra(xsds));
        } catch (SAXException e) {
            LOG.warn("Noe gikk galt med konfigurasjon av skjema fra {}, bruker ikke-validerende marshaller", Arrays.toString(xsds), e);
            return null;
        }
    }

    private Marshaller marshaller() {
        try {
            var marshaller = context.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            marshaller.setEventHandler(new DefaultValidationEventHandler());
            if (schema != null && validateMarshalling) {
                marshaller.setSchema(schema);
            }
            return marshaller;
        } catch (JAXBException e) {
            throw new UnexpectedInputException(
                    format("Feil ved konstruksjon av marshaller fra kontekst %s og skjema  %s", context, schema), e);
        }
    }

    private static Source[] sourcesFra(String... schemas) {
        return Arrays.stream(schemas)
                .map(AbstractJAXBUtil::sourceFra)
                .toArray(Source[]::new);
    }

    private static Source sourceFra(String re) {
        try {
            return new StreamSource(getInputStreamFromResource(re), getURLFromResource(re).toExternalForm());
        } catch (Exception e) {
            throw new UnexpectedInputException(format("Noe gikk galt ved lesing av ressurs %s fra classpath", re), e);
        }
    }

    public static Double tilDoubleFraBigDecimal(JAXBElement<BigDecimal> value) {
        return Optional.ofNullable(value)
                .map(JAXBElement::getValue)
                .map(BigDecimal::doubleValue)
                .orElse(null);
    }

    public static Double tilDoubleFraBigInteger(JAXBElement<BigInteger> value) {
        return Optional.ofNullable(value)
                .map(JAXBElement::getValue)
                .map(BigInteger::doubleValue)
                .orElse(null);
    }

    public static boolean tilBoolean(JAXBElement<Boolean> value) {
        return Optional.ofNullable(value)
                .map(JAXBElement::getValue)
                .orElse(false);
    }

    public static String tilTekst(JAXBElement<String> tekst) {
        return Optional.ofNullable(tekst)
                .map(JAXBElement::getValue)
                .orElse(null);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [context=" + context + ", schema=" + schema + "]";
    }
}
