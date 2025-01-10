package antikyth.taiao.block;

import net.minecraft.block.BlockState;

import java.util.Optional;

public interface Strippable {
    /**
     * Returns the stripped equivalent of this block in its current state.
     *
     * @param state The current state of this block
     * @return The stripped state; empty if the block should not be stripped
     */
    Optional<BlockState> getStrippedState(BlockState state);
}
