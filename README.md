# Power Grid Tweaks

NeoForge 1.21.1 addon for Create: Power Grid.

## Requirements

- Git.
- Internet access for the first Gradle run.
- Java 21 is optional locally: Gradle is configured to use a Java 21 toolchain and can download one automatically through Foojay if none is installed.

## Quick Start

Clone the repository and run the game from the project root:

```bash
./gradlew runClient
```

On Windows:

```bat
gradlew.bat runClient
```

Build the mod jar:

```bash
./gradlew build
```

The output jar is written to `build/libs/`.

## IDE Setup

Open the repository as a Gradle project in IntelliJ IDEA or Eclipse.

If the IDE does not pick up dependencies after import, run:

```bash
./gradlew --refresh-dependencies
```

Then reload the Gradle project in the IDE.

## Useful Tasks

```bash
./gradlew runClient
./gradlew runServer
./gradlew build
./gradlew clean
```

## Notes

- Do not commit local runtime data from `run/`, Gradle caches, IDE folders, or `.ai/`; they are ignored.
- Dependency versions are pinned in `gradle.properties`.
- The Gradle wrapper is included, so a system Gradle installation is not required.
