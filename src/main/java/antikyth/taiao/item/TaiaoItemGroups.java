// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TaiaoItemGroups {
    public static final RegistryKey<ItemGroup> TE_TAIAO_O_AOTEAROA = registerItemGroup(
            Taiao.id("item_group"),
            TaiaoBlocks.CABBAGE_TREE_SAPLING.asItem()
    );

    public static void initialize() {
        Taiao.LOGGER.debug("Registering item groups");

        // Add items to the item group.
        ItemGroupEvents.modifyEntriesEvent(TE_TAIAO_O_AOTEAROA).register(group -> {
            group.add(TaiaoBlocks.KAURI_SAPLING);
            group.add(TaiaoBlocks.KAURI_LEAVES);
            addKauriBuildingBlocks(group::add);

            group.add(TaiaoBlocks.CABBAGE_TREE_SAPLING);
            group.add(TaiaoBlocks.CABBAGE_TREE_LEAVES);
            addCabbageTreeBuildingBlocks(group::add);

            group.add(TaiaoBlocks.MAMAKU_SAPLING);
            group.add(TaiaoBlocks.MAMAKU_LEAVES);
            addMamakuBuildingBlocks(group::add);

            addSpawnEggs(group::add);
        });

        // Spawn eggs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> addSpawnEggs(group::add));
        // Building blocks
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(group -> {
            Consumer<ItemConvertible> add = item -> group.addBefore(Items.STONE, item);

            addKauriBuildingBlocks(add);
            addCabbageTreeBuildingBlocks(add);
            addMamakuBuildingBlocks(add);
        });
        // Natural blocks
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(group -> {
            addLogs(item -> group.addBefore(Items.OAK_LEAVES, item));
            addLeaves(item -> group.addBefore(Items.BROWN_MUSHROOM_BLOCK, item));
            addSaplings(item -> group.addBefore(Items.BROWN_MUSHROOM, item));
        });
    }

    public static void addLogs(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.KAURI_LOG);
        add.accept(TaiaoBlocks.CABBAGE_TREE_LOG);
        add.accept(TaiaoBlocks.MAMAKU_LOG);
    }

    public static void addLeaves(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.KAURI_LEAVES);
        add.accept(TaiaoBlocks.CABBAGE_TREE_LEAVES);
        add.accept(TaiaoBlocks.MAMAKU_LEAVES);
    }

    public static void addSaplings(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.KAURI_SAPLING);
        add.accept(TaiaoBlocks.CABBAGE_TREE_SAPLING);
        add.accept(TaiaoBlocks.MAMAKU_SAPLING);
    }

    public static void addKauriBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.KAURI_LOG);
        add.accept(TaiaoBlocks.KAURI_WOOD);
        add.accept(TaiaoBlocks.STRIPPED_KAURI_LOG);
        add.accept(TaiaoBlocks.STRIPPED_KAURI_WOOD);

        add.accept(TaiaoBlocks.KAURI_PLANKS);
        add.accept(TaiaoBlocks.KAURI_STAIRS);
        add.accept(TaiaoBlocks.KAURI_SLAB);
        add.accept(TaiaoBlocks.KAURI_FENCE);
        add.accept(TaiaoBlocks.KAURI_FENCE_GATE);
        add.accept(TaiaoBlocks.KAURI_PRESSURE_PLATE);
        add.accept(TaiaoBlocks.KAURI_BUTTON);
    }

    public static void addCabbageTreeBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.CABBAGE_TREE_LOG);
        add.accept(TaiaoBlocks.CABBAGE_TREE_WOOD);
        add.accept(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
        add.accept(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);
    }

    public static void addMamakuBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoBlocks.MAMAKU_LOG);
        add.accept(TaiaoBlocks.MAMAKU_WOOD);
        add.accept(TaiaoBlocks.STRIPPED_MAMAKU_LOG);
        add.accept(TaiaoBlocks.STRIPPED_MAMAKU_WOOD);

        add.accept(TaiaoBlocks.MAMAKU_PLANKS);
        add.accept(TaiaoBlocks.MAMAKU_STAIRS);
        add.accept(TaiaoBlocks.MAMAKU_SLAB);
        add.accept(TaiaoBlocks.MAMAKU_FENCE);
        add.accept(TaiaoBlocks.MAMAKU_FENCE_GATE);
        add.accept(TaiaoBlocks.MAMAKU_PRESSURE_PLATE);
        add.accept(TaiaoBlocks.MAMAKU_BUTTON);
    }

    public static void addSpawnEggs(@NotNull Consumer<ItemConvertible> add) {
        add.accept(TaiaoItems.KIWI_SPAWN_EGG);
        add.accept(TaiaoItems.PUUKEKO_SPAWN_EGG);
        add.accept(TaiaoItems.MOA_SPAWN_EGG);
        add.accept(TaiaoItems.KAAKAAPOO_SPAWN_EGG);
    }

    /**
     * Register a new item group with the given {@code id} and {@code icon}.
     * <p>
     * The group's translation key is {@code itemGroup.%namespace.%name}, e.g. {@code itemGroup.taiao.item_group}.
     */
    public static RegistryKey<ItemGroup> registerItemGroup(Identifier id, Item icon) {
        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, id);

        String translationKey = String.format("itemGroup.%s.%s", id.getNamespace(), id.getPath());

        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(icon))
                .displayName(Text.translatable(translationKey))
                .build();

        Registry.register(Registries.ITEM_GROUP, key, group);

        return key;
    }
}
