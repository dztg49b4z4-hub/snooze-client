# Snooze Client (Fabric, MC 1.21.11)

1. Werte in gradle.properties von https://fabricmc.net/develop uebernehmen
2. `./gradlew build` -> build/libs/snooze-client-*.jar in den mods-Ordner (+ Fabric API)
3. Im Spiel: Rechts-Shift = GUI. Linksklick = Modul an/aus, Rechtsklick = Settings, Header ziehen = verschieben.

Neues Modul: Klasse in module/modules/ erstellen (extends Module) und in ModuleManager registrieren.

## Jar bauen ohne Setup (GitHub)
1. Neues Repo auf github.com anlegen, den Inhalt dieses Ordners hochladen.
2. Tab "Actions" -> "build" laeuft automatisch (oder "Run workflow").
3. Nach ~2 Min: Artifact "snooze-client-jar" herunterladen, entpacken.
4. Die Datei `snooze-client-0.1.0.jar` (NICHT die -sources.jar) in `.minecraft/mods` legen,
   zusammen mit Fabric Loader + Fabric API fuer 1.21.11.
