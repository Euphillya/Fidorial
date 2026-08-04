package fr.euphyllia.fidorial.server.tests;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.WorldConstants;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.testing.ScenarioTestHelper;
import fr.fidorial.testing.annotation.ScenarioTest;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;

public final class BlockStateTests {

    private static final Key STONE = Key.key("stone");

    @ScenarioTest(timeoutTicks = 20)
    public static void belowWorldIsAir(final ScenarioTestHelper helper) {
        final World world = helper.world();
        final BlockStateRegistry registry = FidorialServer.getInstance().blockStateRegistry();
        final BlockPos pos = new BlockPos(0, WorldConstants.MIN_Y - 1, 0);

        final BlockState actual = registry.byId(world.getBlockStateId(pos));
        helper.assertTrue(actual.isAir(),
                "Expected " + pos + " to be air (unset), but found " + actual.name());
    }

    @ScenarioTest(timeoutTicks = 40)
    public static void settingBlockStateUpdatesWorld(final ScenarioTestHelper helper) {
        final World world = helper.world();
        final BlockPos pos = new BlockPos(0, -60, 0);
        final int stoneId = stoneId();

        helper.sequence()
                .execute(() -> world.setBlockStateId(pos, stoneId))
                .waitUntil(() -> helper.assertBlockAt(pos, STONE))
                .build();
    }

    private static int stoneId() {
        final BlockStateRegistry registry = FidorialServer.getInstance().blockStateRegistry();
        return registry.networkId(BlockState.of(STONE));
    }
}
