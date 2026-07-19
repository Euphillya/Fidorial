package fr.euphyllia.fidorial.testplugin;

import fr.fidorial.Server;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.player.BlockBreakEvent;
import fr.fidorial.event.player.BlockPlaceEvent;
import fr.fidorial.event.player.PlayerChatEvent;
import fr.fidorial.event.player.PlayerJoinEvent;
import fr.fidorial.event.player.PlayerQuitEvent;
import fr.fidorial.event.server.ServerStartedEvent;
import fr.fidorial.event.server.ServerStoppingEvent;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginContext;
import fr.fidorial.scheduler.RegionTps;
import fr.fidorial.service.ServicePriority;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.World;
import fr.fidorial.world.generation.WorldGenerator;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import fr.euphyllia.fidorial.testplugin.terrain.HillsGenerator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public final class TestPlugin implements Plugin {

    private static final long SEED = 20260716L;
    private static final int BASE_HEIGHT = 64;
    private static final int AMPLITUDE = 24;
    private static final int SEA_LEVEL = 60;

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private final AtomicLong eventCount = new AtomicLong();
    private @Nullable PluginContext context;
    private @Nullable ComponentLogger logger;
    private @Nullable Server server;
    private volatile @Nullable PregenTask task;

    @Override
    public void onLoad(PluginContext context) {
        this.context = context;
        this.logger = context.logger();
        this.server = context.server();

        context.services().register(WorldGenerator.class,
                new HillsGenerator(SEED, BASE_HEIGHT, AMPLITUDE, SEA_LEVEL), this);
        context.logger().info("Generateur de collines enregistre (seed={})", SEED);

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

    private void msg(CommandSender sender, String miniMessageText) {
        sender.sendMessage(MM.deserialize(miniMessageText));
    }

    private void msg(Player player, String miniMessageText) {
        player.sendMessage(MM.deserialize(miniMessageText));
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
            msg(e.player(), "[TestPlugin] Bienvenue " + e.player().name()
                    + " ! Tape /apitest pour tester l'API.");
        });

        events.subscribe(PlayerQuitEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] quit de {}", e.player().name());
        });

        events.subscribe(PlayerChatEvent.class, EventPriority.HIGH, e -> {
            eventCount.incrementAndGet();
            String raw = PLAIN.serialize(e.message());
            if (raw.equalsIgnoreCase("!cancel")) {
                e.setCancelled(true);
                msg(e.player(), "[TestPlugin] Message annule (test Cancellable OK).");
            } else if (raw.startsWith("!upper ")) {
                e.setMessage(Component.text(raw.substring(7).toUpperCase(Locale.ROOT)));
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
        server.commands().register("pregen", (sender, label, args) -> {
            if (args.length == 0) {
                msg(sender, "Usage : /" + label + " start <rayon> [centreX centreZ] | stop | status");
                return;
            }
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "start" -> start(sender, args);
                case "stop" -> stop(sender);
                case "status" -> status(sender);
                default -> msg(sender, "Sous-commande inconnue : " + args[0]);
            }
        });

        server.commands().register("apitest", (sender, label, args) -> {
            if (!sender.hasPermission("testplugin.use")) {
                msg(sender, "[TestPlugin] Permission testplugin.use manquante.");
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
        msg(sender, "[TestPlugin] /apitest <info|tps|worlds|players|service|schedule|perms>");
    }

    private void info(CommandSender sender) {
        msg(sender, "[TestPlugin] MC " + server.minecraftVersion()
                + " | protocole " + server.protocolVersion()
                + " | running=" + server.isRunning()
                + " | plugins charges=" + server.plugins().loaded().size()
                + " | events observes=" + eventCount.get());
    }

    private void tps(CommandSender sender) {
        List<? extends RegionTps> snapshots = server.scheduler().tpsSnapshots();
        if (snapshots.isEmpty()) {
            msg(sender, "[TestPlugin] Aucune region active.");
            return;
        }
        for (RegionTps tps : snapshots) {
            msg(sender, String.format(Locale.ROOT,
                    "[TestPlugin] %s section(%d,%d) tps=%.1f mspt=%.2f queued=%d",
                    tps.world(), tps.sectionX(), tps.sectionZ(),
                    tps.tps(), tps.msptAvg(), tps.queuedTasks()));
        }
    }

    private void worlds(CommandSender sender) {
        String names = server.worlds().stream()
                .map(w -> w.key().toString())
                .collect(Collectors.joining(", "));
        msg(sender, "[TestPlugin] " + server.worlds().size() + " monde(s) : " + names);
    }

    private void players(CommandSender sender) {
        var players = server.onlinePlayers();
        String names = players.stream().map(Player::name).collect(Collectors.joining(", "));
        msg(sender, "[TestPlugin] " + players.size() + " joueur(s) : "
                + (names.isEmpty() ? "(aucun)" : names));
    }

    private void service(CommandSender sender) {
        var found = server.services().find(CounterService.class);
        if (found.isEmpty()) {
            msg(sender, "<red>[TestPlugin] ECHEC : CounterService introuvable !</red>");
            return;
        }
        long value = found.get().increment();
        msg(sender, "[TestPlugin] ServiceRegistry OK, compteur = " + value);
    }

    private void schedule(CommandSender sender) {
        World world = server.worlds().stream().findFirst().orElse(null);
        if (world == null) {
            msg(sender, "[TestPlugin] Aucun monde disponible pour tester le scheduler.");
            return;
        }
        ChunkPos spawnChunk = new ChunkPos(0, 0);
        String worldName = world.key().value();
        msg(sender, "[TestPlugin] Tache planifiee dans 40 ticks (~2s)...");
        server.scheduler().executeDelayed(worldName, spawnChunk, () -> {
            boolean owned = server.scheduler().isOwnedByCurrentThread(worldName, spawnChunk);
            msg(sender, "[TestPlugin] Scheduler OK ! ownedByCurrentThread=" + owned);
            logger.info("[TestPlugin] tache differee executee (owned={})", owned);
        }, 40L);
    }

    private void perms(CommandSender sender) {
        msg(sender, "[TestPlugin] " + sender.name()
                + " | console=" + sender.isConsole()
                + " | testplugin.use=" + sender.hasPermission("testplugin.use")
                + " | testplugin.admin=" + sender.hasPermission("testplugin.admin"));
        msg(sender, "[TestPlugin] Permissions connues du serveur : "
                + server.plugins().getPermissions().size());
    }

    private void start(CommandSender sender, String[] args) {
        PregenTask running = task;
        if (running != null && running.isRunning()) {
            msg(sender, "<red>Une pre-generation est deja en cours :</red> " + running.status());
            return;
        }
        if (args.length < 2) {
            msg(sender, "Usage : /pregen start <rayon> [centreX centreZ]");
            return;
        }

        int radius;
        try {
            radius = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            msg(sender, "<red>Rayon invalide : " + args[1] + "</red>");
            return;
        }

        int centerX = 0;
        int centerZ = 0;
        World world = null;

        if (args.length >= 4) {
            try {
                centerX = Integer.parseInt(args[2]);
                centerZ = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                msg(sender, "<red>Centre invalide : " + args[2] + " " + args[3] + "</red>");
                return;
            }
        } else if (sender instanceof Player player) {
            // centre par defaut : la position du joueur
            var chunk = player.chunk();
            centerX = chunk.x();
            centerZ = chunk.z();
            world = player.world();
        }
        if (world == null) {
            msg(sender, "<red>Aucun monde cible (lance la commande en jeu ou precise le centre).</red>");
            return;
        }
        int total = (2 * radius + 1) * (2 * radius + 1);
        msg(sender, "Pre-generation de " + total + " chunks (rayon " + radius
                + " autour de " + centerX + "," + centerZ + ")...");

        var pregenTask = new PregenTask(world, context.logger(), centerX, centerZ, radius,
                message -> {
                    context.logger().info("[Pregen] {}", message);
                    try {
                        msg(sender, "<gray>[Pregen]</gray> " + message);
                    } catch (Exception ignored) {
                    }
                });
        task = pregenTask;
        pregenTask.start();
    }

    private void stop(CommandSender sender) {
        PregenTask running = task;
        if (running == null || !running.isRunning()) {
            msg(sender, "<red>Aucune pre-generation en cours.</red>");
            return;
        }
        running.cancel();
        msg(sender, "Arret de la pre-generation demande.");
    }

    private void status(CommandSender sender) {
        PregenTask running = task;
        if (running == null || !running.isRunning()) {
            msg(sender, "Aucune pre-generation en cours.");
            return;
        }
        msg(sender, "Pre-generation : " + running.status());
    }
}
