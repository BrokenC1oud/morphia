package dev.morphia.aggregation.experimental.codecs.stages;

import dev.morphia.aggregation.experimental.expressions.impls.Fields;
import dev.morphia.aggregation.experimental.stages.Group;
import dev.morphia.aggregation.experimental.stages.Group.GroupId;
import dev.morphia.mapping.Mapper;

import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;

import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.document;

public class GroupCodec extends StageCodec<Group> {

    public GroupCodec(Mapper mapper) {
        super(mapper);
    }

    @Override
    public Class<Group> getEncoderClass() {
        return Group.class;
    }

    @Override
    protected void encodeStage(BsonWriter writer, Group group, EncoderContext encoderContext) {
        document(writer, () -> {
            GroupId id = group.getId();
            if (id != null) {
                writer.writeName("_id");
                if (id.getDocument() != null) {
                    id.getDocument().encode(getMapper(), writer, encoderContext);
                } else {
                    if (id.getField() != null) {
                        id.getField().encode(getMapper(), writer, encoderContext);
                    }
                }
            } else {
                writer.writeNull("_id");
            }

            Fields<Group> fields = group.getFields();
            if (fields != null) {
                fields.encode(getMapper(), writer, encoderContext);
            }

        });
    }
}
