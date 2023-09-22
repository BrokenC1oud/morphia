package dev.morphia.aggregation.codecs.stages;

import dev.morphia.MorphiaDatastore;
import dev.morphia.aggregation.stages.Set;

import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;

public class SetStageCodec extends StageCodec<Set> {
    public SetStageCodec(MorphiaDatastore datastore) {
        super(datastore);
    }

    @Override
    public Class<Set> getEncoderClass() {
        return Set.class;
    }

    @Override
    protected void encodeStage(BsonWriter writer, Set value, EncoderContext encoderContext) {
        value.getDocument().encode(getDatastore(), writer, encoderContext);
    }
}
