package fr.euphyllia.fidorial.server.codecs.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.CommonCodecs;
import fr.fidorial.world.biome.BiomeDefinition;
import fr.fidorial.world.biome.BiomeEffects;
import fr.fidorial.world.biome.GrassColorModifier;
import fr.fidorial.world.biome.TemperatureModifier;
import fr.fidorial.world.environment.EnvironmentAttributes;
import io.papermc.adventurex.nbt.dfu.BinaryTagOps;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class BiomeCodecs {

}
