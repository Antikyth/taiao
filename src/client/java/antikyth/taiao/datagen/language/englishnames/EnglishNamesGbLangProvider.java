// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language.englishnames;

import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.jetbrains.annotations.NotNull;

public class EnglishNamesGbLangProvider extends FabricLanguageProvider {
	public EnglishNamesGbLangProvider(
		FabricDataOutput dataOutput,
		String languageCode
	) {
		super(dataOutput, languageCode);
	}

	@Override
	public void generateTranslations(@NotNull TranslationBuilder builder) {
		// Other blocks
		builder.add(TaiaoBlocks.RAUPOO, "Bulrush");
	}
}
