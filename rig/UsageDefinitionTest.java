package rig;

import api.UsageDefinition;
import api.UsageDefinition.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for UsageDefinition.
 */
public class UsageDefinitionTest {

  @Test
  public void testUsageDefinitionCreation() {
    UsageDefinition.Builder builder = UsageDefinition.builder("test");
    builder.define("fname");
    builder.define("lname", Optional.TRUE); // optional
    UsageDefinition definition = builder.build();
    Assert.assertEquals("test", definition.getDirectiveName());
    Assert.assertEquals(2, definition.getArguments().size());
  }

  @Test
  public void testMultipleUsageDefinitions() {
    UsageDefinition.Builder builder = UsageDefinition.builder("aggregate-stats");
    builder.define("byteSizeCol");
    builder.define("timeDurationCol");
    builder.define("outputSizeCol");
    builder.define("outputTimeCol");

    UsageDefinition def = builder.build();

    Assert.assertEquals("aggregate-stats", def.getDirectiveName());
    Assert.assertTrue(def.getArguments().containsKey("byteSizeCol"));
    Assert.assertTrue(def.getArguments().containsKey("outputTimeCol"));
    Assert.assertEquals(4, def.getArguments().size());
  }
}
