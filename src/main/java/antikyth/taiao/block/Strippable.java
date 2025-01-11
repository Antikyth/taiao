package antikyth.taiao.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface Strippable {
    Supplier<BiMap<Block, Block>> STRIPPED_BLOCKS = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(TaiaoBlocks.CABBAGE_TREE_LOG, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
            .put(TaiaoBlocks.CABBAGE_TREE_WOOD, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD)
            .build());

    /**
     * Returns the stripped equivalent of this block in its current state.
     *
     * @param state The current state of this block
     * @return The stripped state; empty if the block should not be stripped
     */
    static Optional<BlockState> getStrippedState(BlockState state) {
        return getStrippedBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    static Optional<Block> getStrippedBlock(Block block) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get().get(block));
    }

    default Optional<BlockState> getStrippedResult(BlockState state) {
        return getStrippedState(state);
    }
}
