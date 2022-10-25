package dev.morphia.aggregation.experimental.expressions.impls;

import java.util.List;

import dev.morphia.mapping.Mapper;

import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;

import static java.util.Arrays.asList;

public class ArrayLiteral extends ArrayExpression {
    private final List<Expression> values;

    public ArrayLiteral(Expression... values) {
        super("unused", null);
        this.values = asList(values);
    }

    @Override
    public void encode(Mapper mapper, BsonWriter writer, EncoderContext encoderContext) {
        Codec codec = mapper.getCodecRegistry().get(values.getClass());
        encoderContext.encodeWithChildContext(codec, writer, values);
    }
}
