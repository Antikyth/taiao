package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class TaiaoBlocks {
    public static final Block CABBAGE_TREE_LEAVES = registerFlammable(
            "cabbage_tree_leaves",
            Blocks.createLeavesBlock(BlockSoundGroup.GRASS),
            true,
            30, 60
    );

    public static final Block STRIPPED_CABBAGE_TREE_LOG = registerFlammable(
            "stripped_cabbage_tree_log",
            createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN),
            true,
            5, 5
    );
    public static final Block STRIPPED_CABBAGE_TREE_WOOD = registerFlammable(
            "stripped_cabbage_tree_wood",
            createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN),
            true,
            5, 5
    );
    public static final Block CABBAGE_TREE_LOG = registerFlammable(
            "cabbage_tree_log",
            createThinLogBlock(MapColor.OAK_TAN, MapColor.STONE_GRAY),
            true,
            5, 5
    );
    public static final Block CABBAGE_TREE_WOOD = registerFlammable(
            "cabbage_tree_wood",
            createThinLogBlock(MapColor.OAK_TAN, MapColor.STONE_GRAY),
            true,
            5, 5
    );

    public static void initialize() {
    }

    public static ThinLogBlock createThinLogBlock(
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

    public static Block registerFlammable(
            String name,
            Block block,
            boolean registerItem,
            int burnChance,
            int spreadChance
    ) {
        block = register(name, block, registerItem);

        FlammableBlockRegistry.getDefaultInstance().add(block, burnChance, spreadChance);

        return block;
    }

    public static Block register(String name, Block block, boolean registerItem) {
        Identifier id = Identifier.of(Taiao.MOD_ID, name);

        if (registerItem) {
            BlockItem item = new BlockItem(block, new Item.Settings());

            Registry.register(Registries.ITEM, id, item);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }
}
