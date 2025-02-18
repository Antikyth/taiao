// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.HiinakiBlock;
import antikyth.taiao.block.LongBlockHalf;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.world.gen.feature.config.HiinakiFeatureConfig;
import antikyth.taiao.world.gen.loot.TaiaoLootContextTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.NotNull;

public class HiinakiFeature extends Feature<HiinakiFeatureConfig> {
	public HiinakiFeature() {
		super(HiinakiFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(@NotNull FeatureContext<HiinakiFeatureConfig> context) {
		Random random = context.getRandom();
		HiinakiFeatureConfig config = context.getConfig();
		StructureWorldAccess world = context.getWorld();

		Direction facing = Direction.Type.HORIZONTAL.random(random);

		BlockPos frontPos = context.getOrigin();
		BlockPos backPos = frontPos.offset(LongBlockHalf.FRONT.getDirectionTowardsOtherHalf(facing));

		if (
			world.getBlockState(frontPos).isReplaceable() && world.isWater(frontPos)
				&& world.getBlockState(backPos).isReplaceable() && world.isWater(backPos)
		) {
			BlockState frontState = TaiaoBlocks.HIINAKI.getDefaultState()
				.with(HiinakiBlock.WATERLOGGED, true)
				.with(HiinakiBlock.FACING, facing);

			if (frontState.canPlaceAt(world, frontPos)) {
				BlockState backState = frontState.with(HiinakiBlock.HALF, LongBlockHalf.BACK);

				world.setBlockState(frontPos, frontState, Block.NOTIFY_LISTENERS);
				world.setBlockState(backPos, backState, Block.NOTIFY_LISTENERS);

				if (world.getBlockEntity(frontPos) instanceof HiinakiBlockEntity blockEntity) {
					// Determine whether to add nothing, bait, or a trapped entity to the hīnaki
					HiinakiFeatureConfig.Contents contents = config.weights().get(random);

					switch (contents) {
						// Fill with bait from loot table
						case BAIT -> {
							ServerWorld serverWorld = world.toServerWorld();

							LootTable baitTable = serverWorld.getServer()
								.getLootManager()
								.getLootTable(config.baitLootTableId());

							LootContextParameterSet parameters = new LootContextParameterSet.Builder(serverWorld)
								.add(LootContextParameters.ORIGIN, Vec3d.ofCenter(frontPos))
								.add(LootContextParameters.BLOCK_STATE, frontState)
								.add(LootContextParameters.BLOCK_ENTITY, blockEntity)
								.build(TaiaoLootContextTypes.TRAP_BAIT);

							ObjectArrayList<ItemStack> baitStacks = baitTable.generateLoot(parameters);

							for (ItemStack bait : baitStacks) {
								if (blockEntity.hasBait()) {
									Taiao.LOGGER.warn("Tried to overfill a generated hīnaki");
									break;
								} else if (!blockEntity.addBait(null, bait)) {
									Taiao.LOGGER.warn(
										"Tried to put '{}' in a generated hīnaki, but it isn't in the '{}' tag", bait,
										TaiaoItemTags.HIINAKI_BAIT
									);
									break;
								}
							}
						}

						// Trap an entity type from the provider
						case TRAPPED_ENTITY -> {
							EntityType<?> entityType = config.trappedEntityProvider().get(random);

							blockEntity.setTrappedEntityType(entityType);
						}
					}
				}

				return true;
			}
		}

		return false;
	}
}
