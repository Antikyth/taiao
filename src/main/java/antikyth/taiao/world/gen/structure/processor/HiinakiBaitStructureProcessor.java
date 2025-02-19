// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure.processor;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.HiinakiBlock;
import antikyth.taiao.block.LongBlockHalf;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.entity.HiinakiBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HiinakiBaitStructureProcessor extends StructureProcessor {
	public static final Codec<HiinakiBaitStructureProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		DataPool.createCodec(ItemStack.CODEC)
			.fieldOf("stacks")
			.forGetter(processor -> processor.stacks),
		Codec.BOOL
			.fieldOf("replace_existing_bait")
			.forGetter(processor -> processor.replaceExistingBait)
	).apply(instance, HiinakiBaitStructureProcessor::new));

	@NotNull
	protected final DataPool<ItemStack> stacks;
	protected final boolean replaceExistingBait;

	/**
	 * @param stacks              a weighted pool of stacks to use as bait (should either be empty or with a count of 1)
	 * @param replaceExistingBait whether to replace the bait of a hīnaki that already has some
	 */
	public HiinakiBaitStructureProcessor(@NotNull DataPool<ItemStack> stacks, boolean replaceExistingBait) {
		this.stacks = stacks;
		this.replaceExistingBait = replaceExistingBait;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return TaiaoStructureProcessorTypes.HIINAKI_BAIT;
	}

	@Override
	public @Nullable StructureTemplate.StructureBlockInfo process(
		WorldView world,
		BlockPos structurePos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo originalBlockInfo,
		StructureTemplate.@NotNull StructureBlockInfo currentBlockInfo,
		StructurePlacementData data
	) {
		BlockState state = currentBlockInfo.state();

		if (state.isOf(TaiaoBlocks.HIINAKI) && state.get(HiinakiBlock.HALF) == LongBlockHalf.FRONT) {
			NbtCompound nbt = currentBlockInfo.nbt();
			BlockPos pos = currentBlockInfo.pos();
			Random random = data.getRandom(pos);

			if (nbt == null) {
				Taiao.LOGGER.warn("Hīnaki at {} is missing NBT, will not replace bait", structurePos);
			} else {
				// Get a random stack
				ItemStack stack = this.stacks.getDataOrEmpty(random).orElse(null);

				if (stack != null) {
					NbtCompound oldBaitNbt = nbt.getCompound(HiinakiBlockEntity.BAIT_KEY);
					if (oldBaitNbt != null) {
						ItemStack oldStack = ItemStack.fromNbt(oldBaitNbt);

						if (!oldStack.isEmpty()) {
							if (replaceExistingBait) {
								Taiao.LOGGER.debug(
									"Replacing structure's existing hīnaki bait '{}' with '{}' at {}",
									oldStack,
									stack,
									structurePos
								);
							} else {
								Taiao.LOGGER.debug(
									"Hīnaki at {} already has bait '{}', skipping replacement",
									structurePos,
									oldStack
								);

								return currentBlockInfo;
							}
						}
					}

					NbtCompound baitNbt = new NbtCompound();
					stack.writeNbt(baitNbt);

					nbt.put(HiinakiBlockEntity.BAIT_KEY, baitNbt);

					return new StructureTemplate.StructureBlockInfo(pos, state, nbt);
				}
			}
		}

		return currentBlockInfo;
	}
}
