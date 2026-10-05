# Pokébag 🎒

Pokébag adds a little set of trainer-style bags — Normal, Great, Ultra and Master — that give you extra portable storage right in your inventory. Craft the first one, then upgrade it step by step into the next tier as you go.

Built for good old Minecraft 1.12.2 on Forge.

## Versions

| Loader | Minecraft | Mod version | JDK | Source |
|---|---|---|---|---|
| forge | 1.12.2 | 1.0.0 | 8 | [forge/1.12.2](forge/1.12.2) |

## Building from source

Install the JDK listed above, then build from inside the version folder:

```sh
cd forge/1.12.2
./gradlew build
```

On Windows, use `gradlew.bat build` instead. The finished jar lands in `build/libs/`. The very first build will take a little longer, since Gradle needs to download itself and every dependency the project declares.

## About this repository

This repo keeps the source code and resources for the published version, folder by folder. Local caches, test worlds, compiled output and backup copies are intentionally left out — only the real, published code lives here.

## License & credits

Every license, credit and notice file that shipped with this version has been kept as-is. Check the metadata inside the version folder before redistributing — publishing the source here doesn't change any declared license.
