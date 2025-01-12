package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Taiao implements ModInitializer {
    public static final String MOD_NAME = "Taiao";
    public static final String MOD_ID = "taiao";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        TaiaoBlocks.initialize();
        TaiaoItems.initialize();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    public static <T> RegistryKey<T> createRegistryKey(String name, RegistryKey<? extends Registry<T>> registryKey) {
        return RegistryKey.of(registryKey, Identifier.of(MOD_ID, name));
    }

    public static <T> TagKey<T> createTagKey(String name, RegistryKey<? extends Registry<T>> registryKey) {
        return TagKey.of(registryKey, Identifier.of(MOD_ID, name));
    }
}