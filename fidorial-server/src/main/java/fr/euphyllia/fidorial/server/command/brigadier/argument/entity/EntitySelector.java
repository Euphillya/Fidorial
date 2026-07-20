package fr.euphyllia.fidorial.server.command.brigadier.argument.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EntitySelector {

    public enum SortType {
        ARBITRARY,
        NEAREST,
        FURTHEST,
        RANDOM
    }


    private final int maxResults;
    private boolean includesEntities;
    private final boolean selfSelector;
    private final boolean usesSelector;

    private final List<Predicate<Entity>> predicates;

    private final double x;
    private final double y;
    private final double z;

    private final Double minDistance;
    private final Double maxDistance;

    private final Integer dx;
    private final Integer dy;
    private final Integer dz;

    private final SortType sort;

    private String targetName;
    private UUID targetUuid;


    public EntitySelector(
            int maxResults,
            boolean includesEntities,
            boolean selfSelector,
            boolean usesSelector,
            List<Predicate<Entity>> predicates,
            double x,
            double y,
            double z,
            Double minDistance,
            Double maxDistance,
            Integer dx,
            Integer dy,
            Integer dz,
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

        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

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


    public Entity findSingleEntity(
            CommandSource source
    ) throws CommandSyntaxException {

        checkPermissions(source);

        Collection<? extends Entity> entities =
                findEntities(source);

        if (entities.isEmpty()) {
            throw EntityArgumentType.NO_ENTITIES_FOUND.create();
        }

        if (entities.size() > 1) {
            throw EntityArgumentType.ERROR_NOT_SINGLE_ENTITY.create();
        }

        return entities.iterator().next();
    }


    public Player findSinglePlayer(
            CommandSource source
    ) throws CommandSyntaxException {

        checkPermissions(source);

        List<Player> players =
                findPlayers(source);

        if (players.isEmpty()) {
            throw EntityArgumentType.NO_PLAYERS_FOUND.create();
        }

        if (players.size() > 1) {
            throw EntityArgumentType.ERROR_NOT_SINGLE_PLAYER.create();
        }

        return players.getFirst();
    }


    public Collection<? extends Entity> findEntities(
            CommandSource source
    ) throws CommandSyntaxException {

        checkPermissions(source);

        FidorialServer server = (FidorialServer) source.server();
        Collection<? extends Entity> entities;

        if (targetUuid != null) {
            Optional<? extends Entity> entity =
                    server.entityManager()
                            .all()
                            .stream()
                            .filter(e -> e.uuid().equals(targetUuid))
                            .findFirst();

            if (entity.isPresent()) {
                Entity found = entity.get();

                includesEntities = !(found instanceof Player);

                entities = includesEntities
                        ? List.of(found)
                        : server.onlinePlayers();

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

        return result.stream()
                .limit(maxResults)
                .toList();
    }


    public List<Player> findPlayers(CommandSource source)
            throws CommandSyntaxException {

        Collection<? extends Entity> entities = findEntities(source);

        List<Player> players = new ArrayList<>();

        for (Entity entity : entities) {
            if (!(entity instanceof Player player)) {
                throw EntityArgumentType.ERROR_ONLY_PLAYERS_ALLOWED.create();
            }

            players.add(player);
        }

        if (players.isEmpty()) {
            throw EntityArgumentType.NO_PLAYERS_FOUND.create();
        }

        return players;
    }


    private boolean matches(Entity entity, CommandSource source) {

        if (targetUuid != null &&
                !entity.uuid().equals(targetUuid)) {
            return false;
        }

        if (targetName != null) {
            if (!(entity instanceof Player player)) {
                return false;
            }

            if (!player.name().equalsIgnoreCase(targetName)) {
                return false;
            }
        }

        for (Predicate<Entity> predicate : predicates) {
            boolean result = predicate.test(entity);

            if (!result) {
                return false;
            }
        }

        if (minDistance != null || maxDistance != null) {
            double distance = entity.distanceSquared(source.location());

            if (minDistance != null &&
                    distance < minDistance * minDistance) {
                return false;
            }

            if (maxDistance != null &&
                    distance > maxDistance * maxDistance) {
                return false;
            }
        }

        if (dx != null || dy != null || dz != null) {
            double ex = entity.location().x();
            double ey = entity.location().y();
            double ez = entity.location().z();


            if (dx != null &&
                    (ex < x || ex > x + dx)) {
                return false;
            }


            if (dy != null &&
                    (ey < y || ey > y + dy)) {
                return false;
            }


            return dz == null ||
                    (!(ez < z) && !(ez > z + dz));
        }


        return true;
    }


    private void sort(
            List<? extends Entity> entities,
            CommandSource source
    ) {

        switch (sort) {

            case NEAREST ->
                    entities.sort(
                            Comparator.comparingDouble(
                                    e -> e.distanceSquared(source.location())
                            )
                    );


            case FURTHEST ->
                    entities.sort(
                            Comparator.comparingDouble(
                                    (Entity e) ->
                                            e.distanceSquared(source.location())
                            ).reversed()
                    );


            case RANDOM ->
                    Collections.shuffle(entities);


            case ARBITRARY ->
            {}
        }
    }


    private void checkPermissions(
            CommandSource source
    ) throws CommandSyntaxException {

        if (!usesSelector) {
            return;
        }


        if (!source.sender()
                .hasPermission("minecraft.command.selector")) {

            throw EntityArgumentType
                    .SELECTORS_NOT_PERMITTED
                    .create();
        }
    }
}
