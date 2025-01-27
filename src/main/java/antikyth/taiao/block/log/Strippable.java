// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.log;

import antikyth.taiao.block.TaiaoBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * An interface to provide stripping functionality for blocks that do not have an
 * {@link net.minecraft.state.property.Properties#AXIS AXIS} property.
 * <p>
 * For blocks that <i>do</i> have an {@link net.minecraft.state.property.Properties#AXIS AXIS} property,
 * {@link net.fabricmc.fabric.api.registry.StrippableBlockRegistry StrippableBlockRegistry} can and should be used
 * instead.
 */
public interface Strippable {
    Supplier<BiMap<Block, Block>> STRIPPED_BLOCKS = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(TaiaoBlocks.CABBAGE_TREE_LOG, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
            .put(TaiaoBlocks.CABBAGE_TREE_WOOD, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD)
            .put(TaiaoBlocks.MAMAKU_LOG, TaiaoBlocks.STRIPPED_MAMAKU_LOG)
            .put(TaiaoBlocks.MAMAKU_WOOD, TaiaoBlocks.STRIPPED_MAMAKU_WOOD)
            .build());

    static Optional<BlockState> getStrippedState(BlockState state) {
        return getStrippedBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    static Optional<Block> getStrippedBlock(Block block) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get().get(block));
    }

    /**
     * Returns the stripped equivalent of this block in its current state.
     *
     * @param state The current state of this block
     * @return The stripped state; empty if the block should not be stripped
     */
    default Optional<BlockState> getStrippedResult(BlockState state) {
        return getStrippedState(state);
    }
}
