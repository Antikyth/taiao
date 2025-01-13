// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.feature.CabbageTreeSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TaiaoBlocks {
    public static final Block CABBAGE_TREE_SAPLING = register(
            Taiao.id("cabbage_tree_sapling"),
            new SaplingBlock(new CabbageTreeSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)),
            true
    );
    public static final Block POTTED_CABBAGE_TREE_SAPLING = register(
            Taiao.id("potted_cabbage_tree_sapling"),
            Blocks.createFlowerPotBlock(CABBAGE_TREE_SAPLING),
            false
    );

    public static final Block CABBAGE_TREE_LEAVES = flammable(
            register(
                    Taiao.id("cabbage_tree_leaves"),
                    Blocks.createLeavesBlock(BlockSoundGroup.GRASS),
                    true
            ),
            30, 60
    );

    public static final Block STRIPPED_KAURI_LOG = flammable(
            register(
                    Taiao.id("stripped_kauri_log"),
                    Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.PALE_YELLOW),
                    true
            ), 5, 5
    );
    public static final Block KAURI_LOG = strippable(
            flammable(
                    register(
                            Taiao.id("kauri_log"),
                            Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.STONE_GRAY),
                            true
                    ),
                    5, 5
            ), STRIPPED_KAURI_LOG
    );
    public static final Block STRIPPED_KAURI_WOOD = flammable(
            register(
                    Taiao.id("stripped_kauri_wood"),
                    Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.PALE_YELLOW),
                    true
            ), 5, 5
    );
    public static final Block KAURI_WOOD = strippable(
            flammable(
                    register(
                            Taiao.id("kauri_wood"),
                            Blocks.createLogBlock(MapColor.PALE_YELLOW, MapColor.STONE_GRAY),
                            true
                    ),
                    5, 5
            ), STRIPPED_KAURI_WOOD
    );

    public static final Block STRIPPED_CABBAGE_TREE_LOG = flammable(
            register(
                    Taiao.id("stripped_cabbage_tree_log"),
                    createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN),
                    true
            ),
            5, 5
    );
    public static final Block STRIPPED_CABBAGE_TREE_WOOD = flammable(
            register(
                    Taiao.id("stripped_cabbage_tree_wood"),
                    createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN),
                    true
            ),
            5, 5
    );
    public static final Block CABBAGE_TREE_LOG = flammable(
            register(
                    Taiao.id("cabbage_tree_log"),
                    createThinLogBlock(MapColor.OAK_TAN, MapColor.STONE_GRAY),
                    true
            ),
            5, 5
    );
    public static final Block CABBAGE_TREE_WOOD = flammable(
            register(
                    Taiao.id("cabbage_tree_wood"),
                    createThinLogBlock(MapColor.STONE_GRAY, MapColor.STONE_GRAY),
                    true
            ),
            5, 5
    );

    public static final Block KAURI_PLANKS = flammable(
            register(
                    Taiao.id("kauri_planks"),
                    createPlanks(MapColor.PALE_YELLOW),
                    true
            ), 5, 20
    );

    public static void initialize() {
    }

    public static Block createPlanks(MapColor color) {
        return new Block(AbstractBlock.Settings.create()
                .mapColor(color)
                .instrument(Instrument.BASS)
                .strength(2.0F, 3.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable());
    }

    @Contract("_, _ -> new")
    public static @NotNull ThinLogBlock createThinLogBlock(
            MapColor end,
            MapColor side
    ) {
        return new ThinLogBlock(AbstractBlock.Settings.create()
                .mapColor(state -> state.get(ThinLogBlock.UP) ? end : side)
                .instrument(Instrument.BASS)
                .strength(2f)
                .sounds(BlockSoundGroup.WOOD)
                .burnable());
    }

    public static Block flammable(Block block, int burnChance, int spreadChance) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burnChance, spreadChance);

        return block;
    }

    public static Block strippable(Block unstripped, Block stripped) {
        StrippableBlockRegistry.register(unstripped, stripped);

        return unstripped;
    }

    public static Block register(Identifier id, Block block, boolean registerItem) {
        if (registerItem) {
            BlockItem item = new BlockItem(block, new Item.Settings());

            Registry.register(Registries.ITEM, id, item);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }
}
