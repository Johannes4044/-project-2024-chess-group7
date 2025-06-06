 # HWR OOP Schach Project

This repository contains a student project created for an ongoing lecture on object-oriented
programming with Kotlin at HWR Berlin (summer term 2025).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE of your choice (e.g. IntelliJ IDEA)
2. JDK of choice installed (e.g. through IntelliJ IDEA)
3. Maven installed (e.g. through IntelliJ IDEA)
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

Dieses Projekt ist ein Schachspiel, das im Rahmen einer Vorlesung zur objektorientierten Programmierung mit Kotlin an der HWR Berlin (Sommersemester 2025) entwickelt wurde.
Das Ziel dieses Projekts ist es, ein funktionierendes Schachspiel zu implementieren, das die grundlegenden Regeln des Schachs berücksichtigt. Es wurde ein Schachbrett mit Figuren erstellt, die sich gemäß den Schachregeln bewegen können. Das Projekt dient als Übung zur Anwendung von objektorientierten Prinzipien.

## Wichtige Funktionen

1. Erstellung eines Schachbretts mit allen Figuren in der Startaufstellung.
2. Implementierung der Bewegungslogik für verschiedene Figuren (z. B. Dame, Turm, Springer).
3. Validierung der Züge basierend auf den Schachregeln (z. B. erlaubte Bewegungen, blockierte Wege).
4. Darstellung des Schachbretts in der Konsole.
5. Möglichkeit, Züge einzugeben und das Spiel zu starten.

## Interessante Probleme

1. Die Implementierung der Bewegungslogik für komplexe Figuren wie die Dame, die sich sowohl diagonal als auch horizontal/vertikal bewegen kann.
2. Sicherstellen, dass Figuren nicht durch andere blockiert werden, außer beim Schlagen.
3. Verwaltung der Spiellogik, um sicherzustellen, dass nur gültige Züge ausgeführt werden.
4. Umgang mit der Darstellung des Schachbretts in der Konsole, um eine klare Übersicht zu gewährleisten.

## Feature-Liste

| Number | Feature                                   | Tests |
|--------|-------------------------------------------|-------|
| 1      | Erstellung eines Schachbretts mit Figuren | /     |
| 2      | Darstellung des Schachbretts              | /     |
| 3      | Bewegungslogik für alle Figuren           | /     |
| 4      | Validierung von Zügen                     | /     |


## Additional dependency your project requires

| Number | Dependency Name  | Dependency Description                     | Why is it necessary?                   |
|--------|------------------|--------------------------------------------|----------------------------------------|
| 1      | Wer kann ziehen  | Nur die Farbe kann ziehen, die am Zug ist  | Damit keiner Farbe zweimal ziehen kann |

## Instructions

[TODO]: (Remove these instructions once you finished your fork's setup.)

## TODO



Use a fork of this repository to do implement your project.

Remember to add this repository as a second remote repository (upstream) and pull from the correct
remotes.
This is necessary, because we might apply changes to this template during the next month.

The following section describes how to add multiple remote repositories to your local repository,
which is cloned from the fork.

### Formatting

The repository contains an IntelliJ IDEA formatter configuration file.
It is located in the `.intellij` folder (not in `.idea`, which is the folder created by IntelliJ IDEA).
To use the formatter, you need to import the configuration into your IntelliJ IDEA settings.

Under **Settings**, go to **Editor**, then **Code Style**, click the **Gear Symbol** next to the Dropdown, click **Import Scheme**, then **IntelliJ IDEA code style XML**. Finally, select the `.intellij/formatter.xml` file.

Make sure to always use the imported `HWR OOP` code style when formatting your code.
Be aware that it might differ from the code style configured in your *Project*, or IntelliJ's *Default* code style.

### Multiple remote repositories

Your local repository should have a reference to both the fork (your own remote repository)
and the original remote repository.
To configure your git remote repositories, use the `git remote` command set.

1. Clone your fork and go enter the repository.

```
git clone <fork-url>
cd <created-folder>
```

1. Now your fork is configured as primary remote repository (origin).
   Next to origin, you should add the original repository as a second remote repository (upstream).

```
git remote add upstream <repository-url>
```

1. Verify that both remotes are configured correctly.
   The following command should list both remotes: origin and upstream.

```
git remote -v
```

1. To fetch changes from all remote repositories, use:

```
git fetch --all
```

1. If there are interesting changes (in e.g. the `main` branch) to merge into your branch, use:

```
git pull upstream main
```

[maven]: https://maven.apache.org/
[just]: https://github.com/casey/just
