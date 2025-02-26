// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure.processor;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.loot.TaiaoLootTables;
import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiaoStructureProcessorLists {
	protected static Map<RegistryKey<StructureProcessorList>, List<StructureProcessor>> STRUCTURE_PROCESSOR_LISTS = new HashMap<>();

	public static final RegistryKey<StructureProcessorList> FARM_MARAE = register(
		Taiao.id("farm_marae"),
		new RuleStructureProcessor(
			List.of(
				new StructureProcessorRule(
					new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.1f),
					AlwaysTrueRuleTest.INSTANCE,
					Blocks.CARROTS.getDefaultState()
				),
				new StructureProcessorRule(
					new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.4f),
					AlwaysTrueRuleTest.INSTANCE,
					Blocks.POTATOES.getDefaultState()
				)
			)
		)
	);
	public static final RegistryKey<StructureProcessorList> STREET_MARAE = register(
		Taiao.id("street_marae"),
		new RuleStructureProcessor(
			ImmutableList.of(
				new StructureProcessorRule(
					new BlockMatchRuleTest(Blocks.DIRT_PATH),
					new BlockMatchRuleTest(Blocks.WATER),
					TaiaoBlocks.RIMU_PLANKS.getDefaultState()
				),
				new StructureProcessorRule(
					new BlockMatchRuleTest(Blocks.DIRT_PATH),
					new BlockMatchRuleTest(Blocks.ICE),
					TaiaoBlocks.RIMU_PLANKS.getDefaultState()
				),
				// Since it is impossible to put dirt paths under jigsaw blocks in the structure without editing the NBT
				// files, we replace dirt found in the structure with dirt paths.
				new StructureProcessorRule(
					new BlockMatchRuleTest(Blocks.DIRT),
					AlwaysTrueRuleTest.INSTANCE,
					Blocks.DIRT_PATH.getDefaultState()
				),
				new StructureProcessorRule(
					new RandomBlockMatchRuleTest(Blocks.DIRT_PATH, 0.1F),
					AlwaysTrueRuleTest.INSTANCE,
					Blocks.GRASS_BLOCK.getDefaultState()
				)
			)
		)
	);
	/**
	 * For the bait placed in {@linkplain TaiaoConfiguredFeatures#HIINAKI hīnaki features}, see
	 * {@link TaiaoLootTables#HIINAKI_BAIT}.
	 */
	public static final RegistryKey<StructureProcessorList> FISHER_MARAE = register(
		Taiao.id("fisher_marae"),
		new HiinakiBaitStructureProcessor(
			DataPool.<ItemStack>builder()
				.add(ItemStack.EMPTY, 2)
				.add(new ItemStack(TaiaoItems.EEL), 1)
				.add(new ItemStack(Items.CHICKEN), 1)
				.add(new ItemStack(Items.FROGSPAWN), 1)
				.build(),
			true
		)
	);

	public static void bootstrap(Registerable<StructureProcessorList> registerable) {
		Taiao.LOGGER.debug("Registering structure processor lists");

		for (Map.Entry<RegistryKey<StructureProcessorList>, List<StructureProcessor>> entry : STRUCTURE_PROCESSOR_LISTS.entrySet()) {
			registerable.register(entry.getKey(), new StructureProcessorList(entry.getValue()));
		}
	}

	public static RegistryKey<StructureProcessorList> register(Identifier id, StructureProcessor... processors) {
		return register(id, List.of(processors));
	}

	public static RegistryKey<StructureProcessorList> register(Identifier id, List<StructureProcessor> processors) {
		RegistryKey<StructureProcessorList> key = RegistryKey.of(RegistryKeys.PROCESSOR_LIST, id);

		STRUCTURE_PROCESSOR_LISTS.put(key, List.copyOf(processors));

		return key;
	}
}
