package no.nav.foreldrepenger.common.domain.serialization;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import no.nav.foreldrepenger.common.error.UnexpectedInputException;

final class JacksonUtils {

    private JacksonUtils() {
    }

    static ArrayNode arrayNode(JsonNode rootNode, String nodeName) {
        return Optional.ofNullable(rootNode.get(nodeName))
                .filter(ArrayNode.class::isInstance)
                .map(ArrayNode.class::cast)
                .orElse(null);
    }

    static String textValue(JsonNode rootNode, String fieldName) {
        return Optional.ofNullable(rootNode.get(fieldName))
                .filter(TextNode.class::isInstance)
                .map(TextNode.class::cast)
                .map(TextNode::textValue)
                .orElse(null);
    }

    static Double doubleValue(DoubleNode rootNode) {
        return Optional.ofNullable(rootNode)
                .map(DoubleNode::doubleValue)
                .orElse(null);
    }

    static Number fromNumber(ObjectNode rootNode) {
        var iterator = rootNode.fields();
        while (iterator.hasNext()) {
            JsonNode entry = iterator.next().getValue();
            if (entry instanceof IntNode i) {
                return i.asInt();
            }
            if (entry instanceof DoubleNode d) {
                return d.asDouble();
            }
        }
        throw new UnexpectedInputException("Ukjent node type %s", rootNode.getClass().getSimpleName());
    }

    static String fromString(ObjectNode rootNode) {
        var iterator = rootNode.fields();
        while (iterator.hasNext()) {
            JsonNode entry = iterator.next().getValue();
            if (entry instanceof TextNode t) {
                return t.asText();
            }
            if (entry instanceof IntNode i) {
                return i.asText();
            }
        }
        throw new UnexpectedInputException("Ukjent node type %s", rootNode.getClass().getSimpleName());
    }
}
