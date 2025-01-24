# Te Taiao o Aotearoa

> _"The Environment of New Zealand"_

A Minecraft mod for Fabric adding biomes containing flora and fauna native to Aotearoa New Zealand.

## Setup

[IntelliJ IDEA](https://www.jetbrains.com/idea/download#community-edition) and the
[Minecraft Development plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) are recommended before
continuing.

1. ### Clone the repo to your computer
   e.g. `git clone https://github.com/Antikyth/taiao`
2. ### Import the project into your IDE
   Ensure the Gradle project is imported - in IntelliJ IDEA, a notification should appear in the bottom right with an
   'Import Gradle Project' button, or you can otherwise click the Gradle tab and press the 'Sync All Gradle Projects'
   button in the top left.
3. ### Generate sources
   You will want to generate sources for vanilla Minecraft code so that your IDE can find references to vanilla code and
   allow you to reference it for working out how to do things. You can do this by running `./gradlew genSources` in the
   project folder, or you can find the `genSources` task under `Tasks > fabric > genSources` in the Gradle tab.
4. ### Run data generation
   Te Taiao o Aotearoa generates the vast majority of its JSON files automatically (see
   [`antikyth.taiao.datagen`](src/client/java/antikyth/taiao/datagen)); these generated files are not included in the
   repo, only the code to generate them. IntelliJ IDEA should have a "Data Generation" run configuration automatically
   imported which you can run; otherwise, you can also run data generation with the `runDatagen` Gradle task with
   `./gradlew runDatagen` or find it under `Tasks > fabric > runDatagen`.

   Data generation also needs to be rerun whenever the data generation code is changed, or when things that use dynamic
   registries (like biomes and features) are modified.
5. ### Launch the game
   A development environment can be launched by running the "Minecraft Client" run configuration or by using the
   `runClient` Gradle task with `./gradlew runClient` or by finding it under `Tasks > fabric > runClient`.

### Adding other mods to the development environment

Once the game has been launched once, a `run` folder will appear in the project root. You can add other mods by putting
their `.jar` files in `run/mods/`. You don't need to add Fabric API because it's already included in the development
environment (along with some other mods: see the `dependencies` section in [`build.gradle`](build.gradle)).

## Attribution

### Sounds

| Sound                                                                | Source                                                                                                  | Link                                                                                                                                          | License                                                   | Changes made                                      |
|----------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------|---------------------------------------------------|
| [Kiwi chirp](src/main/resources/assets/taiao/sounds/mob/kiwi)        | [Department of Conservation](https://www.doc.govt.nz/nature/native-animals/birds/bird-songs-and-calls/) | [female-ni-brown-kiwi.mp3](https://www.doc.govt.nz/globalassets/documents/conservation/native-animals/birds/kiwi-cd/female-ni-brown-kiwi.mp3) | [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/) | Cut into individual sound files, decreased volume |
| [Kākāpō ching](src/main/resources/assets/taiao/sounds/mob/kaakaapoo) | [Department of Conservation](https://www.doc.govt.nz/nature/native-animals/birds/bird-songs-and-calls/) | [kakapo-bill-ching-1.mp3](https://www.doc.govt.nz/globalassets/documents/conservation/native-animals/birds/bird-song/kakapo-bill-ching-1.mp3) | [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/) | Cut into individual sound files, decreased volume |
| [Kākāpō boom](src/main/resources/assets/taiao/sounds/mob/kaakaapoo)  | [Department of Conservation](https://www.doc.govt.nz/nature/native-animals/birds/bird-songs-and-calls/) | [kakapo-20.mp3](https://www.doc.govt.nz/globalassets/documents/conservation/native-animals/birds/bird-song/kakapo-20.mp3)                     | [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/) | Cut into individual sound files                   |

