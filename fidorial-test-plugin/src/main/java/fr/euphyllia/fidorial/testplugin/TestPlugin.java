package fr.euphyllia.fidorial.testplugin;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.event.EventPriority;
import fr.euphyllia.fidorial.api.event.player.*;
import fr.euphyllia.fidorial.api.event.server.ServerStartedEvent;
import fr.euphyllia.fidorial.api.event.server.ServerStoppingEvent;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.plugin.PluginContext;
import fr.euphyllia.fidorial.api.scheduler.RegionTps;
import fr.euphyllia.fidorial.api.service.ServicePriority;
import fr.euphyllia.fidorial.api.world.ChunkPos;
import fr.euphyllia.fidorial.api.world.World;
import org.slf4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public final class TestPlugin implements Plugin {

    private PluginContext context;
    private Logger logger;
    private Server server;

    private final AtomicLong eventCount = new AtomicLong();

    @Override
    public void onLoad(PluginContext context) {
        this.context = context;
        this.logger = context.logger();
        this.server = context.server();

        logger.info("[TestPlugin] onLoad OK - id={} version={} dataFolder={}",
                context.meta().id(), context.meta().version(), context.dataFolder());
    }

    @Override
    public void onEnable() {
        logger.info("[TestPlugin] onEnable - demarrage des tests API");

        registerServices();
        registerEvents();
        registerCommands();

        logger.info("[TestPlugin] pret. Tape /apitest pour lancer les tests interactifs.");
    }

    @Override
    public void onDisable() {
        logger.info("[TestPlugin] onDisable - {} event(s) observe(s) pendant la session", eventCount.get());
        server.commands().unregister("apitest");
    }

    private void registerServices() {
        AtomicLong counter = new AtomicLong();
        CounterService impl = new CounterService() {
            @Override
            public long increment() {
                return counter.incrementAndGet();
            }

            @Override
            public long current() {
                return counter.get();
            }
        };
        server.services().register(CounterService.class, impl, this, ServicePriority.NORMAL);
        logger.info("[TestPlugin] ServiceRegistry: CounterService enregistre = {}",
                server.services().find(CounterService.class).isPresent());
    }

    private void registerEvents() {
        var events = context.events();

        events.subscribe(ServerStartedEvent.class, e ->
                logger.info("[TestPlugin][event] ServerStartedEvent recu, version MC {}",
                        e.server().minecraftVersion()));

        events.subscribe(ServerStoppingEvent.class, e ->
                logger.info("[TestPlugin][event] ServerStoppingEvent recu"));

        events.subscribe(PlayerJoinEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] join de {}", e.player().name());
            e.player().sendMessage("[TestPlugin] Bienvenue " + e.player().name()
                    + " ! Tape /apitest pour tester l'API.");
        });

        events.subscribe(PlayerQuitEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] quit de {}", e.player().name());
        });

        events.subscribe(PlayerChatEvent.class, EventPriority.HIGH, e -> {
            eventCount.incrementAndGet();
            if (e.message().equalsIgnoreCase("!cancel")) {
                e.setCancelled(true);
                e.player().sendMessage("[TestPlugin] Message annule (test Cancellable OK).");
            } else if (e.message().startsWith("!upper ")) {
                e.setMessage(e.message().substring(7).toUpperCase(Locale.ROOT));
            }
        });

        events.subscribe(BlockBreakEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] {} casse un bloc en {}", e.player().name(), e.position());
        });

        events.subscribe(BlockPlaceEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] {} pose un bloc", e.player().name());
        });
    }

    private void registerCommands() {
        server.commands().register("apitest", (sender, label, args) -> {
            if (!sender.hasPermission("testplugin.use")) {
                sender.sendMessage("[TestPlugin] Permission testplugin.use manquante.");
                return;
            }
            String sub = args.length == 0 ? "help" : args[0].toLowerCase(Locale.ROOT);
            switch (sub) {
                case "info" -> info(sender);
                case "tps" -> tps(sender);
                case "worlds" -> worlds(sender);
                case "players" -> players(sender);
                case "service" -> service(sender);
                case "schedule" -> schedule(sender);
                case "perms" -> perms(sender);
                default -> help(sender);
            }
        });
        logger.info("[TestPlugin] Commande /apitest enregistree = {}",
                server.commands().isRegistered("apitest"));
    }

    private void help(CommandSender sender) {
        sender.sendMessage("[TestPlugin] /apitest <info|tps|worlds|players|service|schedule|perms>");
    }

    private void info(CommandSender sender) {
        sender.sendMessage("[TestPlugin] MC " + server.minecraftVersion()
                + " | protocole " + server.protocolVersion()
                + " | running=" + server.isRunning()
                + " | plugins charges=" + server.plugins().loaded().size()
                + " | events observes=" + eventCount.get());
    }

    private void tps(CommandSender sender) {
        List<? extends RegionTps> snapshots = server.scheduler().tpsSnapshots();
        if (snapshots.isEmpty()) {
            sender.sendMessage("[TestPlugin] Aucune region active.");
            return;
        }
        for (RegionTps tps : snapshots) {
            sender.sendMessage(String.format(Locale.ROOT,
                    "[TestPlugin] %s section(%d,%d) tps=%.1f mspt=%.2f queued=%d",
                    tps.world(), tps.sectionX(), tps.sectionZ(),
                    tps.tps(), tps.msptAvg(), tps.queuedTasks()));
        }
    }

    private void worlds(CommandSender sender) {
        String names = server.worlds().stream()
                .map(w -> w.key().toString())
                .collect(Collectors.joining(", "));
        sender.sendMessage("[TestPlugin] " + server.worlds().size() + " monde(s) : " + names);
    }

    private void players(CommandSender sender) {
        var players = server.onlinePlayers();
        String names = players.stream().map(Player::name).collect(Collectors.joining(", "));
        sender.sendMessage("[TestPlugin] " + players.size() + " joueur(s) : "
                + (names.isEmpty() ? "(aucun)" : names));
    }

    private void service(CommandSender sender) {
        var found = server.services().find(CounterService.class);
        if (found.isEmpty()) {
            sender.sendMessage("[TestPlugin] ECHEC : CounterService introuvable !");
            return;
        }
        long value = found.get().increment();
        sender.sendMessage("[TestPlugin] ServiceRegistry OK, compteur = " + value);
    }

    private void schedule(CommandSender sender) {
        World world = server.worlds().stream().findFirst().orElse(null);
        if (world == null) {
            sender.sendMessage("[TestPlugin] Aucun monde disponible pour tester le scheduler.");
            return;
        }
        ChunkPos spawnChunk = new ChunkPos(0, 0);
        String worldName = world.key().value();
        sender.sendMessage("[TestPlugin] Tache planifiee dans 40 ticks (~2s)...");
        server.scheduler().executeDelayed(worldName, spawnChunk, () -> {
            boolean owned = server.scheduler().isOwnedByCurrentThread(worldName, spawnChunk);
            sender.sendMessage("[TestPlugin] Scheduler OK ! ownedByCurrentThread=" + owned);
            logger.info("[TestPlugin] tache differee executee (owned={})", owned);
        }, 40L);
    }

    private void perms(CommandSender sender) {
        sender.sendMessage("[TestPlugin] " + sender.name()
                + " | console=" + sender.isConsole()
                + " | testplugin.use=" + sender.hasPermission("testplugin.use")
                + " | testplugin.admin=" + sender.hasPermission("testplugin.admin"));
        sender.sendMessage("[TestPlugin] Permissions connues du serveur : "
                + server.plugins().getPermissions().size());
    }
}
