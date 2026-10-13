# 🪪 Session ID Login Mod

![GitHub Downloads](https://img.shields.io/github/downloads/FuntaRxD/Fabric-Session-Login/total?style=for-the-badge)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.0%20%E2%80%93%2026.3-brightgreen?style=for-the-badge)
![Loader](https://img.shields.io/badge/Fabric%20Loader-%E2%89%A50.15.0-blue?style=for-the-badge)

A lightweight Fabric client-side mod that adds a GUI to log in using Minecraft session IDs.

> 🔒 No account switching tools, launchers, or file editing required — just paste your session token and you're in.

---

## 🧭 Supported Versions

One source tree, one jar per Minecraft range. Pick the jar whose range covers your game version — every jar declares its own compatibility in `fabric.mod.json`, so only the matching one will load.

| Minecraft         | Jar                                | Java |
|-------------------|------------------------------------|------|
| 1.21 – 1.21.1     | `login-mod-1.21.1.jar`  | 21+  |
| 1.21.2 – 1.21.4   | `login-mod-1.21.4.jar`  | 21+  |
| 1.21.5 – 1.21.8   | `login-mod-1.21.8.jar`  | 21+  |
| 1.21.9 – 1.21.11  | `login-mod-1.21.11.jar` | 21+  |
| 26.1              | `login-mod-26.1.jar`    | 25+  |
| 26.2              | `login-mod-26.2.jar`    | 25+  |
| 26.3              | `login-mod-26.3.jar`    | 25+  |

> 📦 Every jar is attached to each [Release](https://github.com/FuntaRxD/Fabric-Session-Login/releases).
>
> ℹ️ `26.1` is the first **unobfuscated** Minecraft version. The 1.21 ↔ 26 boundary is a hard API break, which is why the mod ships several jars built from the same code instead of a single one.

The original mod for versions 1.21–1.21.5 is also available here — [Majanito/Fabric-Session-Login](https://github.com/Majanito/Fabric-Session-Login).

---

## ✨ Features

- 🧩 Adds a login screen to input your Minecraft session ID (Bearer token)
- ✅ Displays current login status (`Valid` or `Invalid`) with real-time validation
- 💬 Shows username and session info in the Multiplayer screen
- 🧑‍💼 Built-in screen to **edit your session account**:
  - 🔤 Change Minecraft **username**
  - 🖼️ Change Minecraft **skin** via URL
- 🛡️ Protects your main account: edits only allowed for session logins
- 🎯 Fully client-side — no server-side impact
- 🖥️ Clean UI that blends into the default Minecraft style

---

## 📥 Installation

1. Download the jar for your Minecraft version from [Releases](https://github.com/FuntaRxD/Fabric-Session-Login/releases)
2. Place it in your `.minecraft/mods` folder
3. Launch the game using a Fabric-enabled profile

> 💡 Requires [Fabric Loader](https://fabricmc.net/use/) (≥0.15.0) and **Java 21** (Java 25 for 26.x). **Fabric API is not required.**

---

## ⚙️ How It Works

- You’ll find a new **Login** button on the **Multiplayer** screen
- Paste your session token and click Login — no restart needed
- An indicator shows whether the session is valid
- A second button opens the **Edit Account** screen
- There, you can safely update your session account’s name or skin

---

## 🛠️ For Developers

- **Single codebase, many versions** via [Stonecutter](https://stonecutter.kikugie.dev/) — nine Java files survive for every target
- Minecraft code is referenced through **Mojang (official) mappings on every version**, so 1.21.x (obfuscated) and 26.x (unobfuscated) share the same names; only four real API breakpoints are handled with `//? if` conditions
- **Fabric Loom 1.18** + **Gradle 9.8**, Java 21 target for 1.21.x and Java 25 for 26.x

```bash
./gradlew build            # compile every version
./gradlew buildAndCollect  # build + copy jars into build/libs/<version>/
```

Adding a version:
1. Add the checkpoint to `versions(...)` in `settings.gradle.kts`
2. Add a `["<version>"]` block with `mod.mc_compat` / `mod.mc_releases` in `stonecutter.properties.toml`
3. `./gradlew build` and fix any API differences with new `//? if` conditions

---

## 📸 Screenshots

**Multiplayer Screen**  
![Multiplayer Screen](https://i.imgur.com/KB4IyAk.png)

**Login Screen**  
![Login Screen](https://i.imgur.com/TXUwkTj.png)

**Edit Account Screen**  
![Edit Account Screen](https://i.imgur.com/oGlBeXw.png)

---

## 🤝 Contributions

Issues and PRs are welcome! Suggestions and improvements are appreciated — feel free to contribute or discuss ideas.
