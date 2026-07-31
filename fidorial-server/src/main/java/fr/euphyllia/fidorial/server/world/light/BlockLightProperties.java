package fr.euphyllia.fidorial.server.world.light;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockRegistry;
import fr.fidorial.world.block.BlockType;
import fr.fidorial.world.block.Blocks;
import org.jspecify.annotations.Nullable;

public class BlockLightProperties {

    public static final int OPAQUE = 15;

    public static int opacity(final BlockState state) {
        if (state.isAir()) {
            return 0;
        }
        final Resolved resolved = resolve(state);
        return resolved == null ? OPAQUE : resolved.behaviour().lightOpacity(resolved.data());
    }

    public static boolean occludes(final BlockState state) {
        return opacity(state) >= OPAQUE;
    }

    public static int emission(final BlockState state) {
        if (state.isAir()) {
            return 0;
        }
        final Resolved resolved = resolve(state);
        return resolved == null ? 0 : resolved.behaviour().lightEmission(resolved.data());
    }

    @SuppressWarnings("PatternValidation")
    private static @Nullable Resolved resolve(final BlockState state) {
        final BlockRegistry registry;
        try {
            registry = Blocks.registry();
        } catch (final IllegalStateException notBootstrapped) {
            return null;
        }
        final BlockBehaviour behaviour = registry.behaviour(state.name()).orElse(null);
        if (behaviour == null) {
            return null;
        }
        final BlockType type = behaviour.type();
        final BlockData data = type.dataOrNull(state.properties());
        return data == null ? null : new Resolved(behaviour, data);
    }

    private record Resolved(BlockBehaviour behaviour, BlockData data) {
    }
}
