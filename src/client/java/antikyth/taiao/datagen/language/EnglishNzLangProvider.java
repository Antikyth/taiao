// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import antikyth.taiao.advancement.TaiaoAdvancements;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.damage.TaiaoDamageTypes;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import antikyth.taiao.stat.TaiaoStats;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class EnglishNzLangProvider extends EnglishGbLangProvider {
	public EnglishNzLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_nz");
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		super.generateTranslations(builder);

		// Advancements
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.SIT_BACK_AND_RELAX,
			null,
			"Wait for a hīnaki to trap an animal, then interact with the hīnaki to hurt or kill it"
		);
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.A_KIND_HEART,
			null,
			"Break a hīnaki to free the animal trapped inside"
		);
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.A_LITTLE_TRIP,
			null,
			"Break a hīnaki with silk touch while an animal is inside"
		);

		// Other blocks
		builder.add(TaiaoBlocks.HAASTS_EAGLE_NEST, "Hokioi Nest");
		builder.add(TaiaoBlocks.HIINAKI, "Hīnaki");

		// Haast's eagle egg
		builder.add(TaiaoItems.HAASTS_EAGLE_EGG, "Hokioi Egg");
		builder.add(TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG, "Partially Cracked Hokioi Egg");
		builder.add(TaiaoItems.CRACKED_HAASTS_EAGLE_EGG, "Cracked Hokioi Egg");
		EnglishUsLangProvider.addSubtitles(
			builder,
			TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_CRACK,
			"Hokioi egg cracks"
		);
		EnglishUsLangProvider.addSubtitles(
			builder,
			TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_HATCH,
			"Hokioi egg hatches"
		);
		// Item tags
		EnglishUsLangProvider.addItemTag(builder, TaiaoItemTags.HIINAKI_BAIT, "Hīnaki Bait");

		// Birds
		EnglishUsLangProvider.addAnimal(
			builder,
			TaiaoEntities.HAASTS_EAGLE,
			TaiaoItems.HAASTS_EAGLE_SPAWN_EGG,
			TaiaoItemTags.HAASTS_EAGLE_FOOD,
			"Hokioi"
		);
		// Fishes
		EnglishUsLangProvider.addAnimal(builder, TaiaoEntities.EEL, TaiaoItems.EEL_SPAWN_EGG, null, "Tuna");
		builder.add(TaiaoItems.EEL, "Raw Tuna");
		builder.add(TaiaoItems.COOKED_EEL, "Cooked Tuna");
		builder.add(TaiaoItems.EEL_BUCKET, "Bucket of Tuna");

		// Subtitles
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_DEATH, "Tuna dies");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_FLOP, "Tuna flops");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_HURT, "Tuna hurts");

		// Damage types
		EnglishUsLangProvider.addDamageType(
			builder,
			TaiaoDamageTypes.HIINAKI,
			"%1$s died in a hīnaki",
			null,
			"%1$s died in a hīnaki at the hands of %2$s"
		);

		// Stats
		EnglishUsLangProvider.addStat(builder, TaiaoStats.HIINAKI_BAIT_ADDED, "Hīnaki Baited");
		EnglishUsLangProvider.addStat(builder, TaiaoStats.HIINAKI_TRAPPED_ENTITY_HARMED, "Hīnaki Used");
		EnglishUsLangProvider.addStat(builder, TaiaoStats.HIINAKI_TRAPPED_ENTITY_FREED, "Animals Freed from Hīnaki");
	}
}
