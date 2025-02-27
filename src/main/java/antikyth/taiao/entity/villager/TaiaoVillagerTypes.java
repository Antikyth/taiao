// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.villager;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.event.FarmerCompostablesCallback;
import antikyth.taiao.event.VillagerProfessionGatherableItemsCallback;
import antikyth.taiao.event.VillagerUniversalGatherableItemsCallback;
import antikyth.taiao.mixin.entity.villager.FarmerWorkTaskAccessor;
import antikyth.taiao.mixin.entity.villager.VillagerEntityAccessor;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class TaiaoVillagerTypes {
	/**
	 * Māori villagers inhabiting {@linkplain antikyth.taiao.world.gen.structure.TaiaoStructures#VILLAGE_MARAE marae}.
	 */
	public static final VillagerType MAAORI = register(
		Taiao.id("maaori"),
		false,
		TaiaoBiomes.NATIVE_FOREST,
		TaiaoBiomes.KAHIKATEA_SWAMP
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered villager types");

		VillagerUniversalGatherableItemsCallback.EVENT.register(builder -> builder.add(TaiaoBlocks.HARAKEKE));
		VillagerProfessionGatherableItemsCallback.EVENT.register((profession, builder) -> {
			if (profession == VillagerProfession.SHEPHERD) {
				builder.add(TaiaoBlocks.HARAKEKE);
			}
		});

		FarmerCompostablesCallback.EVENT.register(builder -> builder.add(TaiaoBlocks.HARAKEKE));
	}

	public static void addGatherableItems() {
		Taiao.LOGGER.debug("Adding to the villager GATHERABLE_ITEMS set");

		ImmutableSet.Builder<Item> builder = ImmutableSet.<Item>builder()
			.addAll(VillagerEntityAccessor.getGatherableItems());

		VillagerUniversalGatherableItemsCallback.EVENT.invoker()
			.addGatherableItems(items -> builder.addAll(
				Arrays.stream(items)
					.filter(Objects::nonNull)
					.map(ItemConvertible::asItem)
					.iterator()
			));

		VillagerEntityAccessor.setGatherableItems(builder.build());
	}

	public static void addCompostableItems() {
		Taiao.LOGGER.debug("Adding to the farmer COMPOSTABLES list");

		ImmutableList.Builder<Item> builder = ImmutableList.<Item>builder()
			.addAll(FarmerWorkTaskAccessor.getCompostableItems());

		FarmerCompostablesCallback.EVENT.invoker()
			.addCompostableItems(items -> builder.addAll(
				Arrays.stream(items)
					.filter(Objects::nonNull)
					.map(ItemConvertible::asItem)
					.iterator()
			));

		FarmerWorkTaskAccessor.setCompostableItems(builder.build());
	}

	/**
	 * Registers a {@link VillagerType} for the given {@code biomes}.
	 *
	 * @param replace whether to replace the {@link VillagerType} for a biome that already has one
	 */
	@SafeVarargs
	public static VillagerType register(Identifier id, boolean replace, RegistryKey<Biome> @NotNull ... biomes) {
		VillagerType villagerType = Registry.register(Registries.VILLAGER_TYPE, id, new VillagerType(id.toString()));

		for (RegistryKey<Biome> biome : biomes) {
			VillagerType oldVillagerType = VillagerType.BIOME_TO_TYPE.get(biome);

			if (oldVillagerType != null) {
				if (replace) {
					Taiao.LOGGER.debug(
						"Switching villager type for biome '{}' from '{}' to '{}'",
						biome.getValue(),
						oldVillagerType,
						villagerType
					);

					VillagerType.BIOME_TO_TYPE.put(biome, villagerType);
				} else {
					Taiao.LOGGER.warn(
						"Attempted to switch villager type for biome '{}' from '{}' to '{}' without replace flag",
						biome.getValue(),
						oldVillagerType,
						villagerType
					);
				}
			} else {
				VillagerType.BIOME_TO_TYPE.put(biome, villagerType);
			}
		}

		return villagerType;
	}
}
