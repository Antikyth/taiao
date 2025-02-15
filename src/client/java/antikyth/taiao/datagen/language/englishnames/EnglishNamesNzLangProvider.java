// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language.englishnames;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.datagen.language.EnglishUsLangProvider;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.jetbrains.annotations.NotNull;

public class EnglishNamesNzLangProvider extends EnglishNamesGbLangProvider {
	public EnglishNamesNzLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_nz");
	}

	@Override
	public void generateTranslations(@NotNull TranslationBuilder builder) {
		super.generateTranslations(builder);

		// Other blocks
		builder.add(TaiaoBlocks.HARAKEKE_MAT, "Flax Mat");
		// Plants
		builder.add(TaiaoBlocks.HARAKEKE, "Flax");

		// Fishes
		EnglishUsLangProvider.addAnimal(builder, TaiaoEntities.EEL, TaiaoItems.EEL_SPAWN_EGG, null, "Eel");
		builder.add(TaiaoItems.EEL_BUCKET, "Bucket of Eel");

		// Subtitles
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_DEATH, "Eel dies");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_FLOP, "Eel flops");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_HURT, "Eel hurts");
	}
}
