// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.damage.TaiaoDamageTypes;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class EnglishNzLangProvider extends EnglishGbLangProvider {
	public EnglishNzLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_nz");
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		super.generateTranslations(builder);

		// Other blocks
		builder.add(TaiaoBlocks.HIINAKI, "Hīnaki");

		// Item tags
		EnglishUsLangProvider.addItemTag(builder, TaiaoItemTags.HIINAKI_BAIT, "Hīnaki Bait");

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
	}
}
