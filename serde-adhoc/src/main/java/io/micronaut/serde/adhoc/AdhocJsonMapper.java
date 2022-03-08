package io.micronaut.serde.adhoc;

import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonStreamConfig;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.ObjectMapper;
import org.reactivestreams.Processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public class AdhocJsonMapper implements ObjectMapper {
    /**
     * Transform a {@link JsonNode} to a value of the given type.
     *
     * @param tree The input json data.
     * @param type The type to deserialize.
     * @return The deserialized value.
     */
    @Override
    public <T> T readValueFromTree(JsonNode tree, Argument<T> type) throws IOException {
        return null;
    }

    /**
     * Parse and map json from the given stream.
     *
     * @param inputStream The input data.
     * @param type        The type to deserialize to.
     * @return The deserialized object.
     */
    @Override
    public <T> T readValue(InputStream inputStream, Argument<T> type) throws IOException {
        return null;
    }

    /**
     * Parse and map json from the given byte array.
     *
     * @param byteArray The input data.
     * @param type      The type to deserialize to.
     * @return The deserialized object.
     */
    @Override
    public <T> T readValue(byte[] byteArray, Argument<T> type) throws IOException {
        return null;
    }

    /**
     * Create a reactive {@link Processor} that accepts json bytes and parses them as {@link JsonNode}s.
     *
     * @param onSubscribe An additional function to invoke with this processor when the returned processor is subscribed to.
     * @param streamArray Whether to return a top-level json array as a stream of elements rather than a single array.
     * @return The reactive processor.
     */
    @Override
    public Processor<byte[], JsonNode> createReactiveParser(Consumer<Processor<byte[], JsonNode>> onSubscribe, boolean streamArray) {
        return null;
    }

    /**
     * Transform an object value to a json tree.
     *
     * @param value The object value to transform.
     * @return The json representation.
     * @throws IOException If there are any mapping exceptions (e.g. illegal values).
     */
    @Override
    public JsonNode writeValueToTree(Object value) throws IOException {
        return null;
    }

    /**
     * Transform an object value to a json tree.
     *
     * @param type  The object type
     * @param value The object value to transform.
     * @return The json representation.
     * @throws IOException If there are any mapping exceptions (e.g. illegal values).
     */
    @Override
    public <T> JsonNode writeValueToTree(Argument<T> type, T value) throws IOException {
        return null;
    }

    /**
     * Write an object as json.
     *
     * @param outputStream The stream to write to.
     * @param object       The object to serialize.
     */
    @Override
    public void writeValue(OutputStream outputStream, Object object) throws IOException {

    }

    /**
     * Write an object as json.
     *
     * @param outputStream The stream to write to.
     * @param type         The object type
     * @param object       The object to serialize.
     */
    @Override
    public <T> void writeValue(OutputStream outputStream, Argument<T> type, T object) throws IOException {

    }

    /**
     * Write an object as json.
     *
     * @param object The object to serialize.
     * @return The serialized encoded json.
     */
    @Override
    public byte[] writeValueAsBytes(Object object) throws IOException {
        return new byte[0];
    }

    /**
     * Write an object as json.
     *
     * @param type   The object type
     * @param object The object to serialize.
     * @return The serialized encoded json.
     */
    @Override
    public <T> byte[] writeValueAsBytes(Argument<T> type, T object) throws IOException {
        return new byte[0];
    }

    /**
     * @return The configured stream config.
     */
    @Override
    public JsonStreamConfig getStreamConfig() {
        return null;
    }
}
