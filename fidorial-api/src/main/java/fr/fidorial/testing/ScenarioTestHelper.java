package fr.fidorial.testing;

import fr.fidorial.world.BlockPos;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;

public final class ScenarioTestHelper {

    private final World world;
    private final ScenarioTestInfo info;

    ScenarioTestHelper(final World world, final ScenarioTestInfo info) {
        this.world = world;
        this.info = info;
    }

    public World world() {
        return world;
    }

    public void fail(final String reason) {
        throw new ScenarioAssertionException(reason, info.tick());
    }

    public void assertTrue(final boolean condition, final String messageIfFalse) {
        if (!condition) {
            fail(messageIfFalse);
        }
    }

    public void assertBlockAt(final BlockPos pos, final Key expected) {
        final Key actual = world.blockKeyAt(pos).orElse(Key.key("minecraft", "air"));
        assertTrue(expected.equals(actual),
                "Expected " + expected + " at " + pos + " but found " + actual);
    }

    /**
     * Builds a sequence for this test.
     */
    public ScenarioTestSequence.Builder sequence() {
        info.sequenceBuilderCreated();
        return new ScenarioTestSequence.Builder(info);
    }
}
