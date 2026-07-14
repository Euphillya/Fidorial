# Fidorial

[English](#english) | [Français](#français)

[![Servers & Players](https://faststats.dev/embed/default:d01e30ea-8ddc-40f6-b773-24d369336950:servers-and-players.svg?w=960&h=340&theme=dark)](https://faststats.dev/project/fidorial/minecraft-plugin)

---

## English

**Fidorial** is a Minecraft server written **from scratch** in Java — no Mojang code, no forks of existing servers. The long-term goal is to give people who want to modify the game a level of freedom that traditional server software can't offer, with a clean, regionized, multithreaded foundation designed for it from day one.

> ⚠️ **Early stage project.** Fidorial is at the very beginning of its development. Very few gameplay features exist yet, and everything is subject to change.

### What works today

- **Native protocol implementation** — handshake, status (server list ping), login and play phases, targeting Minecraft **26.2** (protocol 776)
- **Mojang authentication** — session validation, packet encryption and compression
- **Dynamic registries** — biomes, dimensions, damage types, entity variants, etc. sent during the configuration phase
- **Flat world** — cobblestone superflat with Anvil-format persistence (region files, NBT), asynchronous chunk loading and streaming around the player
- **Block interaction** — place and break blocks, broadcast to all connected players, saved to disk
- **Creative inventory** — item management with per-player persistence across sessions
- **Regionized multithreaded scheduler** — Folia-inspired: the world is split into independent 32×32-chunk regions, each ticking at 20 TPS on its own worker thread. Player-driven tickets keep regions alive and follow players as they move
- **Commands** — in-game (`/tps`) and interactive console. `/tps` reports per-region TPS, average tick time and pending tasks
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
java -jar fidorial-server/build/libs/fidorial-*.jar
```

For development, you can also run directly:

```bash
./gradlew :fidorial-server:run
```

The server listens on port **25565** by default. Type `tps` in the console to check region health.

### Project structure

| Module            | Purpose                                               |
|-------------------|-------------------------------------------------------|
| `fidorial-api`    | Public API: scheduler, registries, world types        |
| `fidorial-auth`   | Mojang session service and encryption utilities       |
| `fidorial-server` | The server itself: network, protocol, world, commands |

### Contributing

**Contributions are open to everyone.** Whether it's code, testing, documentation or ideas — pull requests and issues are welcome on [GitHub](https://github.com/Euphillya/Fidorial).

Come discuss the project on **[Discord](https://discord.gg/QF8M49qE63)**.

### License

[MIT](LICENCE) © 2026 Euphyllia Bierque

---

## Français

**Fidorial** est un serveur Minecraft écrit **entièrement de zéro** en Java — aucun code Mojang, aucun fork de serveur existant. L'objectif à long terme est d'offrir aux personnes qui souhaitent modifier le jeu une liberté que les serveurs traditionnels ne permettent pas, grâce à des fondations propres, régionalisées et multithread pensées pour ça dès le départ.

> ⚠️ **Projet à ses débuts.** Fidorial est au tout début de son développement. Très peu de fonctionnalités de gameplay existent pour l'instant, et tout est susceptible de changer.

### Ce qui fonctionne aujourd'hui

- **Implémentation native du protocole** — phases handshake, status (ping de la liste des serveurs), login et play, ciblant Minecraft **26.2** (protocole 776)
- **Authentification Mojang** — validation de session, chiffrement et compression des paquets
- **Registres dynamiques** — biomes, dimensions, types de dégâts, variantes d'entités, etc. envoyés pendant la phase de configuration
- **Monde plat** — superflat en cobblestone avec persistance au format Anvil (region files, NBT), chargement asynchrone des chunks et streaming autour du joueur
- **Interaction avec les blocs** — pose et casse de blocs, diffusées à tous les joueurs connectés et sauvegardées sur disque
- **Inventaire créatif** — gestion des items avec persistance par joueur entre les sessions
- **Scheduler multithread régionalisé** — inspiré de Folia : le monde est découpé en régions indépendantes de 32×32 chunks, chacune tickée à 20 TPS sur son propre thread. Des tickets liés aux joueurs maintiennent les régions actives et les suivent dans leurs déplacements
- **Commandes** — en jeu (`/tps`) et console interactive. `/tps` affiche les TPS par région, la durée moyenne de tick et les tâches en attente
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
java -jar fidorial-server/build/libs/fidorial-*.jar
```

Pour le développement, tu peux aussi lancer directement :

```bash
./gradlew :fidorial-server:run
```

Le serveur écoute sur le port **25565** par défaut. Tape `tps` dans la console pour vérifier la santé des régions.

### Structure du projet

| Module            | Rôle                                                      |
|-------------------|-----------------------------------------------------------|
| `fidorial-api`    | API publique : scheduler, registres, types du monde       |
| `fidorial-auth`   | Service de session Mojang et utilitaires de chiffrement   |
| `fidorial-server` | Le serveur lui-même : réseau, protocole, monde, commandes |

### Contribuer

**Les contributions sont ouvertes à tous.** Code, tests, documentation ou idées — les pull requests et issues sont les bienvenues sur [GitHub](https://github.com/Euphillya/Fidorial).

Viens discuter du projet sur **[Discord](https://discord.gg/QF8M49qE63)**.

### Licence

[MIT](LICENCE) © 2026 Euphyllia Bierque