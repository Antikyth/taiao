package antikyth.taiao.datagen;

import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;

public class TaiaoBlockLootTableGenerator extends FabricBlockLootTableProvider {
    protected TaiaoBlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        // Normal blocks
        addDrop(TaiaoBlocks.CABBAGE_TREE_LOG);
        addDrop(TaiaoBlocks.CABBAGE_TREE_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
        addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);

        // TODO: add sapling drop
        addDrop(TaiaoBlocks.CABBAGE_TREE_LEAVES, block -> leavesDrops(block, Blocks.OAK_SAPLING, SAPLING_DROP_CHANCE));
    }
}
