package no.nav.foreldrepenger.common.domain.serialization;

import static no.nav.foreldrepenger.common.domain.serialization.JacksonUtils.fromString;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import no.nav.foreldrepenger.common.domain.Orgnummer;

public class OrgnummerDeserializer extends StdDeserializer<Orgnummer> {
    public OrgnummerDeserializer() {
        this(null);
    }

    public OrgnummerDeserializer(Class<Orgnummer> t) {
        super(t);
    }

    public Orgnummer deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        TreeNode treeNode = p.getCodec().readTree(p);
        if (treeNode instanceof TextNode t) {
            return new Orgnummer(t.asText());
        } else if (treeNode instanceof IntNode i) {
            return new Orgnummer(i.asText());
        } else if (treeNode instanceof ObjectNode o) {
            return new Orgnummer(fromString(o));
        } else {
            throw new IllegalArgumentException(String.format("Ukjent node type [%s]", treeNode.getClass().getSimpleName()));
        }
    }
}
