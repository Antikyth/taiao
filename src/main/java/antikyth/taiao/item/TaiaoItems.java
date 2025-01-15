// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TaiaoItems {
    public static final Item PUUKEKO_SPAWN_EGG = register(
            Taiao.id("puukeko_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.PUUKEKO, 0x111111, 0x073673, new FabricItemSettings())
    );
    public static final Item MOA_SPAWN_EGG = register(
            Taiao.id("moa_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.MOA, 0x2c180c, 0x55361c, new FabricItemSettings())
    );

    public static final RegistryKey<ItemGroup> TAIAO_ITEM_GROUP = registerItemGroup(
            Taiao.id("item_group"),
            TaiaoBlocks.CABBAGE_TREE_SAPLING.asItem()
    );

    public static void initialize() {
        // Add items to the item group.
        ItemGroupEvents.modifyEntriesEvent(TAIAO_ITEM_GROUP).register(group -> {
            group.add(TaiaoBlocks.KAURI_LOG);
            group.add(TaiaoBlocks.STRIPPED_KAURI_LOG);
            group.add(TaiaoBlocks.KAURI_WOOD);
            group.add(TaiaoBlocks.STRIPPED_KAURI_WOOD);
            group.add(TaiaoBlocks.KAURI_PLANKS);

            group.add(TaiaoBlocks.CABBAGE_TREE_SAPLING);
            group.add(TaiaoBlocks.CABBAGE_TREE_LEAVES);

            group.add(TaiaoBlocks.CABBAGE_TREE_LOG);
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
            group.add(TaiaoBlocks.CABBAGE_TREE_WOOD);
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);

            group.add(PUUKEKO_SPAWN_EGG);
            group.add(MOA_SPAWN_EGG);
        });

        // Spawn eggs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> {
            group.add(PUUKEKO_SPAWN_EGG);
            group.add(MOA_SPAWN_EGG);
        });
    }

    /**
     * Register the given {@code item} with the given {@code name} as its item ID.
     */
    public static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    /**
     * Register a new item group with the given {@code key} and {@code icon}.
     * <p>
     * The group's translation key is {@code itemGroup.%namespace.%name}, e.g. {@code itemGroup.taiao.item_group}.
     */
    public static RegistryKey<ItemGroup> registerItemGroup(Identifier id, Item icon) {
        RegistryKey<ItemGroup> key = Taiao.createRegistryKey(id, RegistryKeys.ITEM_GROUP);

        String translationKey = String.format("itemGroup.%s.%s", id.getNamespace(), id.getPath());

        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(icon))
                .displayName(Text.translatable(translationKey))
                .build();

        Registry.register(Registries.ITEM_GROUP, key, group);

        return key;
    }
}
