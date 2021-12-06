package no.nav.foreldrepenger.common.domain.domain;

import static no.nav.foreldrepenger.common.mapper.DefaultJsonMapper.MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SerializationTestBase {

    protected static final Logger LOG = LoggerFactory.getLogger(SerializationTestBase.class);
    protected static ObjectMapper mapper;

    @BeforeAll
    public static void beforeAll() {
        mapper = MAPPER;
    }

    protected static void test(Object obj) {
        test(obj, true);
    }

    protected static void test(Object obj, boolean log) {
        try {
            if (log) {
                LOG.info("{}", obj);
            }
            var serialized = serialize(obj);
            if (log) {
                LOG.info("Serialized as {}", serialized);
            }
            var deserialized = mapper.readValue(serialized, obj.getClass());
            if (log) {
                LOG.info("{}", deserialized);
            }
            assertEquals(obj, deserialized);
        } catch (Exception e) {
            LOG.error("Oops", e);
            fail(obj.getClass().getSimpleName() + " failed");
        }
    }

    private static String serialize(Object obj) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

}
