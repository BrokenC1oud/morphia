package dev.morphia.aggregation.experimental.expressions.impls;

import dev.morphia.mapping.Mapper;

import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;

import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.document;
import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.expression;
import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.value;

public class ConvertExpression extends Expression {
    private final Expression input;
    private final ConvertType to;
    private Expression onError;
    private Expression onNull;

    public ConvertExpression(Expression input, ConvertType to) {
        super("$convert");
        this.input = input;
        this.to = to;
    }

    @Override
    public void encode(Mapper mapper, BsonWriter writer, EncoderContext encoderContext) {
        document(writer, () -> {
            document(writer, getOperation(), () -> {
                expression(mapper, writer, "input", input, encoderContext);
                value(mapper, writer, "to", to.getName(), encoderContext);
                expression(mapper, writer, "onError", onError, encoderContext);
                expression(mapper, writer, "onNull", onNull, encoderContext);
            });
        });
    }

    /**
     * The value to return on encountering an error during conversion, including unsupported type conversions.
     *
     * @param onError the value
     * @return this
     */
    public ConvertExpression onError(Expression onError) {
        this.onError = onError;
        return this;
    }

    /**
     * The value to return if the input is null or missing.
     *
     * @param onNull the value
     * @return this
     */
    public ConvertExpression onNull(Expression onNull) {
        this.onNull = onNull;
        return this;
    }
}
