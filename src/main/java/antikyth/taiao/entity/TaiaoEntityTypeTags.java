// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.ai.goal.FreezeWhenThreatenedGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoEntityTypeTags {
	/**
	 * Entities that can be naturally generated in {@linkplain antikyth.taiao.block.TaiaoBlocks#HIINAKI hīnaki}.
	 */
	public static final TagKey<EntityType<?>> HIINAKI_TRAPPED_ENTITIES = tagKey(Taiao.id("hiinaki_trapped_entities"));

	/**
	 * Mammalian predators, like cats and dogs, which hunt most native birds.
	 */
	public static final TagKey<EntityType<?>> MAMMALIAN_PREDATORS = tagKey(Taiao.id("predators/mammals"));

	/**
	 * Entities that a {@linkplain TaiaoEntities#KIWI kiwi} cannot sleep near.
	 */
	public static final TagKey<EntityType<?>> KIWI_PREDATORS = tagKey(Taiao.id("predators/of/kiwi"));
	/**
	 * Entities that a {@linkplain TaiaoEntities#PUUKEKO pūkeko} will attack if they are near pūkeko chicks.
	 * <p>
	 * Pūkeko will not attack entities that have been tamed.
	 */
	public static final TagKey<EntityType<?>> PUUKEKO_PREDATORS = tagKey(Taiao.id("predators/of/puukeko"));
	/**
	 * Entities that cause a {@linkplain TaiaoEntities#KAAKAAPOO kākāpō} to {@linkplain FreezeWhenThreatenedGoal freeze}.
	 * <p>
	 * Kākāpō will freeze in the presence of predators whether they are tamed or not, even if those tamed predators may
	 * not be allowed to attack them.
	 */
	public static final TagKey<EntityType<?>> KAAKAAPOO_PREDATORS = tagKey(Taiao.id("predators/of/kaakaapoo"));
	/**
	 * Entities that cause an {@linkplain TaiaoEntities#AUSTRALASIAN_BITTERN Australasian bittern} to freeze or flee
	 * from.
	 * <p>
	 * Australasian bitterns will freeze or flee from predators whether they are tamed or not, even if those tamed
	 * predators may not be allowed to attack them.
	 */
	public static final TagKey<EntityType<?>> AUSTRALASIAN_BITTERN_PREDATORS = tagKey(
		Taiao.id("predators/of/australasian_bittern")
	);

	/**
	 * Schooling fish, preyed on by various predators in aquatic and semiaquatic environments.
	 */
	public static final TagKey<EntityType<?>> SCHOOLING_FISH_PREY = tagKey(Taiao.id("prey/schooling_fishes"));
	/**
	 * Entities that {@linkplain TaiaoEntities#AUSTRALASIAN_BITTERN Australasian bitterns} hunt.
	 */
	public static final TagKey<EntityType<?>> AUSTRALASIAN_BITTERN_PREY = tagKey(Taiao.id("prey/of/australasian_bittern"));

	public static final TagKey<EntityType<?>> HAASTS_EAGLE_BABY_PREY = tagKey(Taiao.id("prey/of/haasts_eagle/babies"));
	public static final TagKey<EntityType<?>> HAASTS_EAGLE_GENERAL_PREY = tagKey(Taiao.id("prey/of/haasts_eagle/general"));

	public static TagKey<EntityType<?>> tagKey(Identifier id) {
		return TagKey.of(RegistryKeys.ENTITY_TYPE, id);
	}
}
