// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EnglishGbLangProvider extends FabricLanguageProvider {
	public EnglishGbLangProvider(FabricDataOutput dataOutput, String languageCode) {
		super(dataOutput, languageCode);
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		// Banner patterns
		EnglishUsLangProvider.addBannerPatterns(builder, EnglishGbLangProvider::addBannerPatternGrey);
	}

	public static void addBannerPatternGrey(
		@NotNull TranslationBuilder builder,
		@NotNull RegistryKey<BannerPattern> pattern,
		String name
	) {
		String translationKey = String.format("block.minecraft.banner.%s", pattern.getValue().toShortTranslationKey());

		Function<DyeColor, String> getDyedTranslation = dyeColor -> String.format(
			"%s.%s",
			translationKey,
			dyeColor.getName()
		);

		builder.add(getDyedTranslation.apply(DyeColor.GRAY), String.format("Grey %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.LIGHT_GRAY), String.format("Light Grey %s", name));
	}
}
