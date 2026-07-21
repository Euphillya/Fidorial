<div align="center">
  <h1> Fidorial</h1>

[English](#english) | [Français](#français)

[![VERSION](https://img.shields.io/badge/Minecraft-26.2-blue.svg)](https://github.com/Euphillya/Fidorial)
[![Java](https://img.shields.io/badge/Java-25+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://github.com/Euphillya/Fidorial/blob/master/LICENCE)
[![Servers](https://img.shields.io/endpoint?url=https%3A%2F%2Ffaststats.dev%2Fapi%2Fshields%2Ffidorial%3Fmetric%3Dservers&style=flat)](https://faststats.dev/project/fidorial)
[![Total downloads](https://img.shields.io/endpoint?url=https%3A%2F%2Ffaststats.dev%2Fapi%2Fshields%2Ffidorial%3Fmetric%3Ddownloads&style=flat)](https://faststats.dev/project/fidorial)

[Documentation](https://fidorial.euphyllia.moe) • [GitHub](https://github.com/Euphillya/Fidorial) • [Discord](https://discord.gg/uUJQEB7XNN)

[![Servers & Players](https://faststats.dev/embed/default:d01e30ea-8ddc-40f6-b773-24d369336950:servers-and-players.svg?w=960&h=340&theme=dark)](https://faststats.dev/project/fidorial/minecraft-plugin)
</div>

---

## English

**Fidorial** is a Minecraft server written **from scratch** in Java — no Mojang code, no forks of existing servers. The
long-term goal is to give people who want to modify the game a level of freedom that traditional server software can't
offer, with a clean, regionized, multithreaded foundation designed for it from day one.

> ⚠️ **Early stage project.** Fidorial is at the very beginning of its development. Very few gameplay features exist
> yet, and everything is subject to change — including the plugin API described below.

### What works today

- **Native protocol implementation** — handshake, status (server list ping), login and play phases, targeting Minecraft*
  *26.2** (protocol 776)
- **Mojang authentication** — session validation, packet encryption and compression
- **Dynamic registries** — biomes, dimensions, damage types, entity variants, etc. sent during the configuration phase
- **Flat world** — cobblestone superflat with Anvil-format persistence (region files, NBT), asynchronous chunk loading
  and streaming around the player
- **Block interaction** — place and break blocks, broadcast to all connected players, saved to disk
- **Fluid simulation** — water and lava with source blocks, downward flow priority, horizontal spreading with per-fluid
  drop-off, infinite source formation, and lava/water interaction producing obsidian or cobblestone. Fluid ticks are
  scheduled on the region that owns the block, so a lake spreading in one corner of the map costs nothing to the rest of
  it
- **Game modes** — survival, creative, adventure and spectator, switchable live with `/gamemode` (alias `/gm`),
  in game or from the console. Creative grants flight, invulnerability and instant block breaking; survival respects
  the client-side mining duration (server-side validation will come with the anti-cheat). Each player's game mode is
  persisted separately from the inventory and survives reconnections; the mode given to new players is set with
  `default-game-mode` in `fidorial.properties`
- **Creative inventory** — item management with per-player persistence across sessions. Inventories are exposed to
  plugins as API types (`PlayerInventory`, `ItemStack`) via `player.inventory()`
- **Pluggable player storage** — inventories and player data (game mode, ...) are loaded and saved through the
  `PlayerInventoryStorage` and `PlayerDataStorage` services. The server ships an NBT-file implementation by default;
  a plugin can register its own backend (database, Redis, ...) and every call site picks it up.
  See [Using plugins as mods](#using-plugins-as-mods)
- **Rich text formatting** — MiniMessage-style tags supported natively in every message: colors (`<red>`, `<#ff8800>`),
  decorations (`<bold>`, `<italic>`, ...), fonts, shadow colors, and interactivity (`<click:run_command:'/spawn'>`,
  `<hover:show_text:'...'>`, `<insertion:'...'>`). Messages are serialized as native NBT text components for clients
  and rendered with ANSI colors in the console. Exposed to plugins through the `TextFormatter` service.
  See [Text formatting](#text-formatting)
-
    - **In-game chat** — enables player interaction and supports **Rich text formatting**
- **Weather engine** — vanilla-style rain and thunder cycle with randomized durations, broadcast to all players and
  synced to anyone joining mid-storm. Weather state is persisted in `level.dat` using the vanilla NBT keys, so it
  survives restarts. Controllable in game or from the console with `/weather`, and replaceable by plugins through the
  `WeatherManager` service
- **Regionized multithreaded scheduler** — Folia-inspired: the world is split into independent 32×32-chunk regions, each
  ticking at 20 TPS on its own worker thread. Player-driven tickets keep regions alive and follow players as they move
- **Plugin API** — load JARs at startup, subscribe to events, replace server behaviour through the service registry.
  See [Writing a plugin](#writing-a-plugin)
- **Commands** — in-game (`/tps`, `/weather`, `/gamemode`) and interactive console. `/tps` reports per-region TPS,
  average tick time and pending
  tasks
- **Anonymous metrics** via [FastStats](https://faststats.dev/project/fidorial/minecraft-plugin)

### Requirements

- **Java 25** or newer

### Building

```bash
git clone https://github.com/Euphillya/Fidorial.git
cd Fidorial
./gradlew :fidorial-server:shadowJar
```

The runnable jar is produced in `fidorial-server/build/libs/`. Run it with:

```bash
java -jar fidorial-server/build/libs/Fidorial-*.jar
```

For development, you can also run directly:

```bash
./gradlew :fidorial-server:run
```

### Formatting

Fidorial uses [Spotless](https://github.com/diffplug/spotless) with Palantir Java Format. Check formatting before
committing with:

```bash
./gradlew spotlessCheck
```

Apply the formatter with:

```bash
./gradlew spotlessApply
```

CI runs `spotlessCheck`. Java files are formatted with Palantir; Gradle, Markdown, properties, JSON, TOML, XML and YAML
files are checked for trailing whitespace and a final newline.

On first start, Fidorial writes a `fidorial.properties` next to the jar — port, view distance, world path, online
mode, default game mode and worker thread counts live there. The server listens on port **25565** by default. Type `tps`
in the console to check
region health.

### Writing a plugin

Fidorial has no Forge, no Fabric and no Mixin. Instead of patching server code, a plugin **subscribes to events** and *
*registers services**. That's a deliberate trade: you don't get to rewrite arbitrary bytecode, but your plugin doesn't
break every time the server's internals move, and two plugins touching the same system don't silently corrupt each
other.

Add the API as a dependency, marked `compileOnly` — the server provides it at runtime. It is published on
[repo.euphyllia.moe](https://repo.euphyllia.moe):

```kotlin
repositories {
    maven("https://repo.euphyllia.moe/repository/maven-public/")
}

dependencies {
    compileOnly("fr.euphyllia.fidorial:fidorial-api:0.1.0-SNAPSHOT")
}
```

Describe your plugin in a `fidorial.json` at the root of your jar:

```json
{
  "id": "bedrockguard",
  "name": "Bedrock Guard",
  "version": "1.0.0",
  "main": "com.example.BedrockGuard",
  "authors": [
    "you"
  ],
  "depends": []
}
```

Then implement `Plugin`:

```java
package com.example;

import fr.euphyllia.fidorial.api.event.EventPriority;
import fr.euphyllia.fidorial.api.event.player.BlockBreakEvent;
import fr.euphyllia.fidorial.api.event.player.PlayerJoinEvent;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.plugin.PluginContext;

public final class BedrockGuard implements Plugin {

    private PluginContext ctx;

    @Override
    public void onLoad(PluginContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onEnable() {
        ctx.events().subscribe(PlayerJoinEvent.class, event ->
                event.player().sendMessage("Bedrock is protected below y=0."));

        // Cancelling the event stops the block from ever changing:
        // no packet, no disk write, no fluid update.
        ctx.events().subscribe(BlockBreakEvent.class, EventPriority.HIGH, event -> {
            if (event.position().y() < 0) {
                event.setCancelled(true);
                event.player().sendMessage("You can't break that.");
            }
        });

        ctx.logger().info("Guarding {} world(s)", ctx.server().worlds().size());
    }
}
```

Drop the jar in `plugins/` and start the server. Listeners and services are unregistered automatically when a plugin is
disabled — you don't clean up by hand.

**Two things to know.** Listeners run on the thread of the region that owns the block or entity, so don't block in them:
hand long work to `ctx.server().scheduler()`. And each plugin gets its own classloader, so you can shade dependencies
without colliding with anyone else.

### Text formatting

Every string passed to `sendMessage` supports MiniMessage-style tags out of the box — nothing to import, no component
builder:

```java

public final class SendMessage() {

    public void send(Player player) {
        player.sendMessage("<gold><bold>Shop</bold></gold> <green>Purchase complete!</green>");
        player.sendMessage("<click:run_command:'/spawn'><aqua>Click here</aqua></click> to go back to spawn");
        player.sendMessage("<hover:show_text:'<gray>Last seen: today</gray>'>Steve</hover>");
    }

}
```

Supported tags: the 16 named colors, hex colors (`<#ff8800>` or `<color:#ff8800>`), `<bold>`, `<italic>`,
`<underlined>`, `<strikethrough>`, `<obfuscated>`, `<font:...>`, `<shadow_color:#aarrggbb>`, `<insertion:'...'>`,
`<click:action:'...'>` (open_url, run_command, suggest_command, copy_to_clipboard, change_page),
`<hover:show_text:'...'>` (the tooltip itself can contain tags), closing tags (`</red>`, `</color>`), `<reset>`,
and `\<` to escape a literal `<`. Unknown tags are left untouched. On the client side, messages become native NBT
text components; in the console, tags are rendered as ANSI colors.

The `TextFormatter` service gives plugins the utilities around it:

```java
private void setTextFormater() {
    TextFormatter text = ctx.services().get(TextFormatter.class);

    ctx.logger().info(text.stripTags("<red>Error:</red> details")); // tag-free logs
    player.sendMessage("<yellow>Name: </yellow>" + TextFormatter.escape(userInput)); // no tag injection
}
```

Like every default service, it is registered at `LOWEST` priority — register your own implementation to change how
text is parsed server-wide.

### Using plugins as mods

This is where Fidorial is going. Events let you *observe and veto*; the **service registry** lets you *replace*.

The server registers its own implementations at `LOWEST` priority. Register yours higher and every call site picks it up
instead — no hooks to add, no server code to patch:

```java

@Override
public void onEnable() {
    // From now on, anything that moves fluid asks your implementation.
    ctx.services().register(FluidManager.class, new MyFluidPhysics(), this);
}
```

The rule that makes this work: **the server never calls an implementation directly, always
through `services.get(X.class)`**. As systems land — mobs, AI, world generation, item behaviour — each one ships as a
default service, which means each one is replaceable the day it exists. A plugin that swaps `MobAiService` isn't really
a plugin any more; it's a mod, and it never touched a line of server code.

Storage works the same way. Player inventories and data go through `PlayerInventoryStorage` and `PlayerDataStorage`,
and both work with pure API types (`PlayerInventory`, `ItemStack`, `PlayerData`) — no server internals, no NBT. Want
inventories in a database shared across servers? Implement the interface and register it:

```java

@Override
public void onEnable() {
    // From now on, every inventory load/save goes through your backend.
    ctx.services().register(PlayerInventoryStorage.class, new SqlInventoryStorage(), this);
}

final class SqlInventoryStorage implements PlayerInventoryStorage {
    @Override
    public PlayerInventory load(UUID uuid) {
        PlayerInventory inv = new PlayerInventory();
        // SELECT slot, item_id, count ... then:
        // inv.set(slot, ItemStack.of(Key.parse(itemId), count));
        return inv;
    }

    @Override
    public void save(UUID uuid, PlayerInventory inventory) {
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.get(slot);
            if (!stack.isEmpty()) {
                // INSERT (uuid, slot, stack.id().asString(), stack.count())
            }
        }
    }
}
```

### Project structure

| Module            | Purpose                                                                             |
|-------------------|-------------------------------------------------------------------------------------|
| `fidorial-api`    | Public API: events, services, plugins, entities, scheduler, registries, world types |
| `fidorial-auth`   | Mojang session service and encryption utilities                                     |
| `fidorial-server` | The server itself: network, protocol, world, entities, commands                     |

Plugins should only ever import from `fidorial-api`. If you find yourself needing something out of `fidorial-server`,
that's a gap in the API — please open an issue.

### Contributing

**Contributions are open to everyone.** Whether it's code, testing, documentation or ideas — pull requests and issues
are welcome on [GitHub](https://github.com/Euphillya/Fidorial).

Come discuss the project on **[Discord](https://discord.gg/QF8M49qE63)**.

### License

[MIT](LICENCE) © 2026 Euphyllia Bierque

---

## Français

**Fidorial** est un serveur Minecraft écrit **entièrement de zéro** en Java — aucun code Mojang, aucun fork de serveur
existant. L'objectif à long terme est d'offrir aux personnes qui souhaitent modifier le jeu une liberté que les serveurs
traditionnels ne permettent pas, grâce à des fondations propres, régionalisées et multithread pensées pour ça dès le
départ.

> ⚠️ **Projet à ses débuts.** Fidorial est au tout début de son développement. Très peu de fonctionnalités de gameplay
> existent pour l'instant, et tout est susceptible de changer — y compris l'API de plugins décrite plus bas.

### Ce qui fonctionne aujourd'hui

- **Implémentation native du protocole** — phases handshake, status (ping de la liste des serveurs), login et play,
  ciblant Minecraft **26.2** (protocole 776)
- **Authentification Mojang** — validation de session, chiffrement et compression des paquets
- **Registres dynamiques** — biomes, dimensions, types de dégâts, variantes d'entités, etc. envoyés pendant la phase de
  configuration
- **Monde plat** — superflat en cobblestone avec persistance au format Anvil (region files, NBT), chargement asynchrone
  des chunks et streaming autour du joueur
- **Interaction avec les blocs** — pose et casse de blocs, diffusées à tous les joueurs connectés et sauvegardées sur
  disque
- **Simulation des fluides** — eau et lave avec blocs sources, écoulement vertical prioritaire, étalement horizontal
  avec perte de niveau propre à chaque fluide, formation de sources infinies, et interaction lave/eau produisant
  obsidienne ou cobblestone. Les ticks de fluide sont planifiés sur la région propriétaire du bloc : un lac qui s'étale
  dans un coin de la carte ne coûte rien au reste
- **Modes de jeu** — survie, créatif, aventure et spectateur, changeables à chaud avec `/gamemode` (alias `/gm`),
  en jeu ou depuis la console. Le créatif donne le vol, l'invulnérabilité et la casse instantanée ; la survie respecte
  la durée de minage côté client (la validation côté serveur viendra avec l'anti-cheat). Le mode de jeu de chaque
  joueur est persisté séparément de l'inventaire et survit aux reconnexions ; le mode des nouveaux joueurs se règle
  avec `default-game-mode` dans `fidorial.properties`
- **Inventaire créatif** — gestion des items avec persistance par joueur entre les sessions. Les inventaires sont
  exposés aux plugins comme des types API (`PlayerInventory`, `ItemStack`) via `player.inventory()`
- **Stockage joueur remplaçable** — les inventaires et les données joueur (mode de jeu, ...) sont chargés et
  sauvegardés via les services `PlayerInventoryStorage` et `PlayerDataStorage`. Le serveur fournit par défaut une
  implémentation en fichiers NBT ; un plugin peut enregistrer son propre backend (base de données, Redis, ...) et
  tous les points d'appel l'utilisent. Voir [Utiliser les plugins comme des mods](#utiliser-les-plugins-comme-des-mods)
- **Formatage de texte riche** — balises style MiniMessage supportées nativement dans tous les messages : couleurs
  (`<red>`, `<#ff8800>`), décorations (`<bold>`, `<italic>`, ...), polices, couleurs d'ombre et interactivité
  (`<click:run_command:'/spawn'>`, `<hover:show_text:'...'>`, `<insertion:'...'>`). Les messages sont sérialisés en
  composants texte NBT natifs pour les clients et rendus en couleurs ANSI dans la console. Exposé aux plugins via le
  service `TextFormatter`. Voir [Formatage de texte](#formatage-de-texte)
- **Chat en jeu** — permet d'interagir entre joueur et supportant le **Formatage de texte riche**
- **Moteur météo** — cycle pluie/orage à la vanilla avec durées aléatoires, diffusé à tous les joueurs et synchronisé
  pour quiconque se connecte en pleine averse. L'état météo est persisté dans le `level.dat` avec les clés NBT vanilla,
  il survit donc aux redémarrages. Contrôlable en jeu ou depuis la console avec `/weather`, et remplaçable par un plugin
  via le service `WeatherManager`
- **Scheduler multithread régionalisé** — inspiré de Folia : le monde est découpé en régions indépendantes de 32×32
  chunks, chacune tickée à 20 TPS sur son propre thread. Des tickets liés aux joueurs maintiennent les régions actives
  et les suivent dans leurs déplacements
- **API de plugins** — chargement de JARs au démarrage, abonnement aux événements, remplacement du comportement du
  serveur via le registre de services. Voir [Écrire un plugin](#écrire-un-plugin)
- **Commandes** — en jeu (`/tps`, `/weather`, `/gamemode`) et console interactive. `/tps` affiche les TPS par région, la
  durée moyenne de tick et
  les tâches en attente
- **Métriques anonymes** via [FastStats](https://faststats.dev/project/fidorial/minecraft-plugin)

### Prérequis

- **Java 25** ou plus récent

### Compilation

```bash
git clone https://github.com/Euphillya/Fidorial.git
cd Fidorial
./gradlew :fidorial-server:shadowJar
```

Le jar exécutable est produit dans `fidorial-server/build/libs/`. Lance-le avec :

```bash
java -jar fidorial-server/build/libs/Fidorial-*.jar
```

Pour le développement, tu peux aussi lancer directement :

```bash
./gradlew :fidorial-server:run
```

Au premier démarrage, Fidorial écrit un `fidorial.properties` à côté du jar — port, distance de vue, chemin du monde,
online mode, mode de jeu par défaut et nombre de threads s'y trouvent. Le serveur écoute sur le port **25565** par
défaut. Tape `tps` dans la
console pour vérifier la santé des régions.

### Écrire un plugin

Fidorial n'a ni Forge, ni Fabric, ni Mixin. Plutôt que de patcher le code du serveur, un plugin **s'abonne à des
événements** et **enregistre des services**. C'est un compromis assumé : tu ne peux pas réécrire n'importe quel
bytecode, mais ton plugin ne casse pas à chaque fois que les entrailles du serveur bougent, et deux plugins qui touchent
au même système ne se corrompent pas mutuellement en silence.

Ajoute l'API en dépendance, en `compileOnly` — le serveur la fournit à l'exécution. Elle est publiée sur
[repo.euphyllia.moe](https://repo.euphyllia.moe) :

```kotlin
repositories {
    maven("https://repo.euphyllia.moe/repository/maven-public/")
}

dependencies {
    compileOnly("fr.euphyllia.fidorial:fidorial-api:0.1.0-SNAPSHOT")
}
```

Décris ton plugin dans un `fidorial.json` à la racine de ton jar :

```json
{
  "id": "bedrockguard",
  "name": "Bedrock Guard",
  "version": "1.0.0",
  "main": "com.exemple.BedrockGuard",
  "authors": [
    "toi"
  ],
  "depends": []
}
```

Puis implémente `Plugin` :

```java
package com.exemple;

import fr.euphyllia.fidorial.api.event.EventPriority;
import fr.euphyllia.fidorial.api.event.player.BlockBreakEvent;
import fr.euphyllia.fidorial.api.event.player.PlayerJoinEvent;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.plugin.PluginContext;

public final class BedrockGuard implements Plugin {

    private PluginContext ctx;

    @Override
    public void onLoad(PluginContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onEnable() {
        ctx.events().subscribe(PlayerJoinEvent.class, event ->
                event.player().sendMessage("La bedrock est protégée sous y=0."));

        // Annuler l'événement empêche le bloc de changer du tout :
        // pas de paquet, pas d'écriture disque, pas de mise à jour des fluides.
        ctx.events().subscribe(BlockBreakEvent.class, EventPriority.HIGH, event -> {
            if (event.position().y() < 0) {
                event.setCancelled(true);
                event.player().sendMessage("Tu ne peux pas casser ça.");
            }
        });

        ctx.logger().info("{} monde(s) surveillé(s)", ctx.server().worlds().size());
    }
}
```

Dépose le jar dans `plugins/` et démarre le serveur. Les listeners et services sont retirés automatiquement quand un
plugin est désactivé — pas de nettoyage à la main.

**Deux choses à savoir.** Les listeners tournent sur le thread de la région propriétaire du bloc ou de l'entité : ne
bloque pas dedans, confie le travail long à `ctx.server().scheduler()`. Et chaque plugin a son propre classloader, donc
tu peux embarquer tes dépendances sans entrer en collision avec les autres.

### Formatage de texte

Toute chaîne passée à `sendMessage` supporte les balises style MiniMessage sans rien faire de spécial — aucun import,
pas de builder de composants :

```java

public final class SendMessage() {

    public void send(Player player) {
        player.sendMessage("<gold><bold>Boutique</bold></gold> <green>Achat effectué !</green>");
        player.sendMessage("<click:run_command:'/spawn'><aqua>Clique ici</aqua></click> pour retourner au spawn");
        player.sendMessage("<hover:show_text:'<gray>Dernière connexion : hier</gray>'>Steve</hover>");
    }
}
```

Balises supportées : les 16 couleurs nommées, les couleurs hex (`<#ff8800>` ou `<color:#ff8800>`), `<bold>`,
`<italic>`, `<underlined>`, `<strikethrough>`, `<obfuscated>`, `<font:...>`, `<shadow_color:#aarrggbb>`,
`<insertion:'...'>`, `<click:action:'...'>` (open_url, run_command, suggest_command, copy_to_clipboard, change_page),
`<hover:show_text:'...'>` (le tooltip peut lui-même contenir des balises), les balises fermantes (`</red>`,
`</color>`), `<reset>`, et `\<` pour afficher un `<` littéral. Les balises inconnues sont laissées telles quelles.
Côté client, les messages deviennent des composants texte NBT natifs ; dans la console, les balises sont rendues en
couleurs ANSI.

Le service `TextFormatter` fournit aux plugins les utilitaires autour :

```java
private void setTextFormater() {
    TextFormatter text = ctx.services().get(TextFormatter.class);

    ctx.logger().info(text.stripTags("<red>Erreur :</red> détails")); // logs sans balises
    player.sendMessage("<yellow>Pseudo : </yellow>" + TextFormatter.escape(saisieJoueur)); // pas d'injection de balises
}
```

Comme tout service par défaut, il est enregistré en priorité `LOWEST` — enregistre ta propre implémentation pour
changer la façon dont le texte est interprété sur tout le serveur.

### Utiliser les plugins comme des mods

C'est là que Fidorial veut aller. Les événements permettent d'**observer et d'opposer un veto** ; le **registre de
services** permet de **remplacer**.

Le serveur enregistre ses propres implémentations en priorité `LOWEST`. Enregistre la tienne plus haut et tous les
points d'appel la prennent à la place — aucun hook à ajouter, aucun code serveur à patcher :

```java

@Override
public void onEnable() {
    // Désormais, tout ce qui déplace un fluide interroge ton implémentation.
    ctx.services().register(FluidManager.class, new MaPhysiqueDesFluides(), this);
}
```

La règle qui fait tenir tout ça : **le serveur n'appelle jamais une implémentation en direct, toujours
via `services.get(X.class)`**. À mesure que les systèmes arrivent — mobs, IA, génération de monde, comportement des
items — chacun est livré comme un service par défaut, donc chacun est remplaçable le jour où il existe. Un plugin qui
échange `MobAiService` n'est plus vraiment un plugin : c'est un mod, et il n'a pas touché une ligne de code serveur.

Le stockage fonctionne de la même façon. Les inventaires et données des joueurs passent par `PlayerInventoryStorage`
et `PlayerDataStorage`, et les deux travaillent avec des types API purs (`PlayerInventory`, `ItemStack`,
`PlayerData`) — pas d'entrailles du serveur, pas de NBT. Tu veux des inventaires en base de données partagés entre
serveurs ? Implémente l'interface et enregistre-la :

```java

@Override
public void onEnable() {
    // Désormais, chaque chargement/sauvegarde d'inventaire passe par ton backend.
    ctx.services().register(PlayerInventoryStorage.class, new SqlInventoryStorage(), this);
}

final class SqlInventoryStorage implements PlayerInventoryStorage {
    @Override
    public PlayerInventory load(UUID uuid) {
        PlayerInventory inv = new PlayerInventory();
        // SELECT slot, item_id, count ... puis :
        // inv.set(slot, ItemStack.of(Key.parse(itemId), count));
        return inv;
    }

    @Override
    public void save(UUID uuid, PlayerInventory inventory) {
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.get(slot);
            if (!stack.isEmpty()) {
                // INSERT (uuid, slot, stack.id().asString(), stack.count())
            }
        }
    }
}
```

### Structure du projet

| Module            | Rôle                                                                                        |
|-------------------|---------------------------------------------------------------------------------------------|
| `fidorial-api`    | API publique : événements, services, plugins, entités, scheduler, registres, types du monde |
| `fidorial-auth`   | Service de session Mojang et utilitaires de chiffrement                                     |
| `fidorial-server` | Le serveur lui-même : réseau, protocole, monde, entités, commandes                          |

Un plugin ne devrait jamais importer autre chose que `fidorial-api`. Si tu as besoin de quelque chose venant de
`fidorial-server`, c'est un manque dans l'API — ouvre une issue.

### Contribuer

**Les contributions sont ouvertes à tous.** Code, tests, documentation ou idées — les pull requests et issues sont les
bienvenues sur [GitHub](https://github.com/Euphillya/Fidorial).

Viens discuter du projet sur **[Discord](https://discord.gg/QF8M49qE63)**.

### Licence

[MIT](LICENCE) © 2026 Euphyllia Bierque
