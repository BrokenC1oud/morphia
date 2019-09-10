package dev.morphia;


import dev.morphia.annotations.AlsoLoad;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.query.FindOptions;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;


public class TestSingleToMultipleConversion extends TestBase {
    @Test
    public void testBasicType() {
        getDs().find(HasSingleString.class)
               .remove();
        getDs().save(new HasSingleString());
        Assert.assertNotNull(getDs().find(HasSingleString.class)
                                    .execute(new FindOptions().limit(1))
                                    .next());
        Assert.assertEquals(1, getDs().find(HasSingleString.class).count());
        final HasManyStringsArray hms = getDs().find(HasManyStringsArray.class)
                                               .execute(new FindOptions().limit(1))
                                               .next();
        Assert.assertNotNull(hms);
        Assert.assertNotNull(hms.strings);
        Assert.assertEquals(1, hms.strings.length);

        final HasManyStringsList hms2 = getDs().find(HasManyStringsList.class)
                                               .execute(new FindOptions().limit(1))
                                               .next();
        Assert.assertNotNull(hms2);
        Assert.assertNotNull(hms2.strings);
        Assert.assertEquals(1, hms2.strings.size());
    }

    @Test
    public void testEmbeddedType() {
        getDs().save(new HasEmbeddedStringy());
        Assert.assertNotNull(getDs().find(HasEmbeddedStringy.class)
                                    .execute(new FindOptions().limit(1))
                                    .next());
        Assert.assertEquals(1, getDs().find(HasEmbeddedStringy.class).count());
        final HasEmbeddedStringyArray has = getDs().find(HasEmbeddedStringyArray.class)
                                                   .execute(new FindOptions().limit(1))
                                                   .next();
        Assert.assertNotNull(has);
        Assert.assertNotNull(has.hss);
        Assert.assertEquals(1, has.hss.length);

        final HasEmbeddedStringySet has2 = getDs().find(HasEmbeddedStringySet.class)
                                                  .execute(new FindOptions().limit(1))
                                                  .next();
        Assert.assertNotNull(has2);
        Assert.assertNotNull(has2.hss);
        Assert.assertEquals(1, has2.hss.size());
    }

    @Embedded
    private static class HasString {
        private String s = "foo";
    }

    @Entity(value = "B", useDiscriminator = false)
    private static class HasEmbeddedStringy {
        @Id
        private ObjectId id;
        private HasString hs = new HasString();
    }

    @Entity(value = "B", useDiscriminator = false)
    private static class HasEmbeddedStringyArray {
        @Id
        private ObjectId id;
        @AlsoLoad("hs")
        private HasString[] hss;
    }

    @Entity(value = "B", useDiscriminator = false)
    private static class HasEmbeddedStringySet {
        @Id
        private ObjectId id;
        @AlsoLoad("hs")
        private Set<HasString> hss;
    }

    @Entity(value = "A", useDiscriminator = false)
    private static class HasSingleString {
        @Id
        private ObjectId id;
        private String s = "foo";
    }

    @Entity(value = "A", useDiscriminator = false)
    private static class HasManyStringsArray {
        @Id
        private ObjectId id;
        @AlsoLoad("s")
        private String[] strings;
    }

    @Entity(value = "A", useDiscriminator = false)
    private static class HasManyStringsList {
        @Id
        private ObjectId id;
        @AlsoLoad("s")
        private List<String> strings;
    }
}
