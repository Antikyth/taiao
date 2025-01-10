package antikyth.taiao;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class TaiaoBlocks {
    public static final Block CABBAGE_TREE_LEAVES = register("cabbage_tree_leaves", Blocks.createLeavesBlock(BlockSoundGroup.GRASS), true);

    public static Block register(String name, Block block, boolean registerItem) {
        Identifier id = Identifier.of(Taiao.MOD_ID, name);

        if (registerItem) {
            BlockItem item = new BlockItem(block, new Item.Settings());

            Registry.register(Registries.ITEM, id, item);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {}
}
