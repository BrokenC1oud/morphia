package dev.morphia.aggregation.experimental.expressions.impls;

import java.util.ArrayList;
import java.util.List;

import dev.morphia.mapping.Mapper;

import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;

import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.expression;
import static dev.morphia.aggregation.experimental.expressions.Expressions.field;

public class Fields<T> {
    private final List<PipelineField> fields = new ArrayList<>();
    private T owner;

    protected Fields(T owner) {
        this.owner = owner;
    }

    private Fields() {
    }

    public static <T> Fields<T> on(T owner) {
        return new Fields<>(owner);
    }

    public T add(String name) {
        fields.add(new PipelineField(name, field(name)));
        return owner;
    }

    public T add(String name, Expression expression) {
        fields.add(new PipelineField(name, expression));
        return owner;
    }

    public void encode(Mapper mapper, BsonWriter writer, EncoderContext encoderContext) {
        for (PipelineField field : fields) {
            expression(mapper, writer, field.getName(), field.getValue(), encoderContext);
        }
    }

    public List<PipelineField> getFields() {
        return fields;
    }

    public int size() {
        return fields.size();
    }

}
