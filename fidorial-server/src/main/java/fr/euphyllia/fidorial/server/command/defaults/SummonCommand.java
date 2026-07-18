package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.EntityType;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.mob.Mobs;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public final class SummonCommand implements CommandExecutor {

    @Override
    @SuppressWarnings("PatternValidation")
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.summon")) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        if (args.length != 1 && args.length != 4) {
            sender.sendMessage(Component.translatable("command.summon.usage", Component.text(label)));
            return;
        }

        FidorialServer server = FidorialServer.getInstance();
        Location location;
        if (args.length == 4) {
            try {
                location = new Location(Double.parseDouble(args[1]),
                        Double.parseDouble(args[2]), Double.parseDouble(args[3]), 0f, 0f);
            } catch (NumberFormatException e) {
                sender.sendMessage(Component.translatable("command.summon.badcoords"));
                return;
            }
        } else if (sender instanceof ServerPlayer player) {
            location = player.location();
        } else {
            sender.sendMessage(Component.translatable("command.summon.console", Component.text(label)));
            return;
        }

        Key key = Key.key(args[0].toLowerCase(Locale.ROOT));
        EntityType type = EntityTypes.get(key);
        if (type == null) {
            sender.sendMessage(Component.translatable("command.summon.unknown", Component.text(key.asString())));
            return;
        }
        if (!Mobs.isMob(type)) {
            sender.sendMessage(Component.translatable("command.summon.notmob", Component.text(key.asString())));
            return;
        }

        ServerWorld world = sender instanceof ServerPlayer player && player.world() instanceof ServerWorld w
                ? w : server.worldManager().overworld();

        Mob mob = Mobs.create(type, server.entityIds().allocate(), world, location);
        server.spawnEntity(mob);
        sender.sendMessage(Component.translatable("command.summon.done",
                Component.text(key.value()), Component.text(mob.entityId())));
    }
}
