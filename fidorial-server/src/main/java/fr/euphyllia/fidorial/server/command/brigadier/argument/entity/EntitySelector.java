package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.DoubleRange;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EntitySelector {

    public enum SortType {
        ARBITRARY, NEAREST, FURTHEST, RANDOM
    }

    private final int maxResults;
    private boolean includesEntities;
    private final boolean selfSelector;
    private final boolean usesSelector;

    private final List<Predicate<Entity>> predicates;

    private final Double x;
    private final Double y;
    private final Double z;

    private final DoubleRange distance;

    private final Double dx;
    private final Double dy;
    private final Double dz;

    private final SortType sort;

    private final String targetName;
    private final UUID targetUuid;

    public EntitySelector(
            int maxResults,
            boolean includesEntities,
            boolean selfSelector,
            boolean usesSelector,
            List<Predicate<Entity>> predicates,
            Double x,
            Double y,
            Double z,
            DoubleRange distance,
            Double dx,
            Double dy,
            Double dz,
            SortType sort,
            String targetName,
            UUID targetUuid
    ) {
        this.maxResults = maxResults;
        this.includesEntities = includesEntities;
        this.selfSelector = selfSelector;
        this.usesSelector = usesSelector;
        this.predicates = predicates;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.sort = sort;
        this.targetName = targetName;
        this.targetUuid = targetUuid;
    }

    public int maxResults() {
        return maxResults;
    }

    public boolean includesEntities() {
        return includesEntities;
    }

    public boolean selfSelector() {
        return selfSelector;
    }

    public boolean usesSelector() {
        return usesSelector;
    }

    public Entity findSingleEntity(CommandSource source) throws CommandSyntaxException {
        checkPermissions(source);

        Collection<? extends Entity> entities = findEntities(source);

        if (entities.isEmpty()) {
            throw EntityArgument.NO_ENTITIES_FOUND.create();
        }
        if (entities.size() > 1) {
            throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
        }

        return entities.iterator().next();
    }

    public Player findSinglePlayer(CommandSource source) throws CommandSyntaxException {
        checkPermissions(source);

        List<Player> players = findPlayers(source);

        if (players.isEmpty()) {
            throw EntityArgument.NO_PLAYERS_FOUND.create();
        }
        if (players.size() > 1) {
            throw EntityArgument.ERROR_NOT_SINGLE_PLAYER.create();
        }

        return players.getFirst();
    }

    public Collection<? extends Entity> findEntities(CommandSource source) throws CommandSyntaxException {
        checkPermissions(source);

        FidorialServer server = (FidorialServer) source.server();
        Collection<? extends Entity> entities;

        if (targetUuid != null) {
            Optional<? extends Entity> entity = server.entityManager().all().stream()
                    .filter(e -> e.uuid().equals(targetUuid))
                    .findFirst();

            if (entity.isPresent()) {
                Entity found = entity.get();
                includesEntities = !(found instanceof Player);
                entities = includesEntities ? List.of(found) : server.onlinePlayers();
            } else {
                includesEntities = false;
                entities = server.onlinePlayers();
            }

        } else if (targetName != null) {
            includesEntities = false;
            entities = server.onlinePlayers();

        } else if (selfSelector && source.sender() instanceof Player player) {
            includesEntities = false;
            entities = List.of(player);

        } else if (includesEntities) {
            entities = server.entityManager().all();

        } else {
            entities = server.onlinePlayers();
        }

        List<Entity> result = entities.stream()
                .filter(it -> matches(it, source))
                .map(Entity.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));

        sort(result, source);

        return result.stream().limit(maxResults).toList();
    }

    public List<Player> findPlayers(CommandSource source) throws CommandSyntaxException {
        Collection<? extends Entity> entities = findEntities(source);
        List<Player> players = new ArrayList<>();

        for (Entity entity : entities) {
            if (!(entity instanceof Player player)) {
                throw EntityArgument.ERROR_ONLY_PLAYERS_ALLOWED.create();
            }
            players.add(player);
        }

        if (players.isEmpty()) {
            throw EntityArgument.NO_PLAYERS_FOUND.create();
        }

        return players;
    }

    private boolean matches(Entity entity, CommandSource source) {
        if (targetUuid != null && !entity.uuid().equals(targetUuid)) {
            return false;
        }

        if (targetName != null) {
            if (!(entity instanceof Player player)) return false;
            if (!player.name().equalsIgnoreCase(targetName)) return false;
        }

        for (Predicate<Entity> predicate : predicates) {
            if (!predicate.test(entity)) return false;
        }

        if (distance != null) {
            double distSqr = entity.distanceSquared(source.location());
            if (!distance.matchesSqr(distSqr)) return false;
        }

        if (dx != null || dy != null || dz != null) {
            double originX = x != null ? x : source.location().x();
            double originY = y != null ? y : source.location().y();
            double originZ = z != null ? z : source.location().z();

            double ex = entity.location().x();
            double ey = entity.location().y();
            double ez = entity.location().z();

            if (dx != null) {
                double lo = Math.min(originX, originX + dx);
                double hi = Math.max(originX, originX + dx);
                if (ex < lo || ex > hi) return false;
            }
            if (dy != null) {
                double lo = Math.min(originY, originY + dy);
                double hi = Math.max(originY, originY + dy);
                if (ey < lo || ey > hi) return false;
            }
            if (dz != null) {
                double lo = Math.min(originZ, originZ + dz);
                double hi = Math.max(originZ, originZ + dz);
                if (ez < lo || ez > hi) return false;
            }
        }

        return true;
    }

    private void sort(List<? extends Entity> entities, CommandSource source) {
        switch (sort) {
            case NEAREST -> entities.sort(Comparator.comparingDouble(e -> e.distanceSquared(source.location())));
            case FURTHEST -> entities.sort(
                    Comparator.comparingDouble((Entity e) -> e.distanceSquared(source.location())).reversed());
            case RANDOM -> Collections.shuffle(entities);
            case ARBITRARY -> { }
        }
    }

    private void checkPermissions(CommandSource source) throws CommandSyntaxException {
        if (!usesSelector) return;
        if (!source.sender().hasPermission("minecraft.command.selector")) {
            throw EntityArgument.SELECTORS_NOT_PERMITTED.create();
        }
    }
}
