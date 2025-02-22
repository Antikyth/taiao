// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.banner;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Banner {
	@Nullable
	protected final Text name;
	protected final Item base;
	protected final boolean hidePatterns;
	protected final BannerPattern.Patterns patterns;

	protected ItemStack stack;

	public Banner(@Nullable Identifier id, Item base, boolean hidePatterns, BannerPattern.Patterns patterns) {
		this(
			id == null ? null : Text.translatable(id.toTranslationKey("block")).formatted(Formatting.GOLD),
			base,
			hidePatterns,
			patterns
		);
	}

	public Banner(@Nullable Text name, Item base, boolean hidePatterns, BannerPattern.Patterns patterns) {
		this.name = name;
		this.base = base;
		this.hidePatterns = hidePatterns;
		this.patterns = patterns;
	}

	public @NotNull ItemStack getOrCreateStack() {
		if (this.stack == null) {
			ItemStack stack = new ItemStack(base);
			NbtCompound stackNbt = new NbtCompound();

			stackNbt.put("Patterns", this.patterns.toNbt());
			BlockItem.setBlockEntityNbt(stack, BlockEntityType.BANNER, stackNbt);
			if (this.hidePatterns) stack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
			if (this.name != null) stack.setCustomName(name);

			this.stack = stack;
		}

		return this.stack;
	}

	public @Nullable Text getCustomName() {
		return this.name;
	}

	public Item getBase() {
		return this.base;
	}

	public boolean hidePatterns() {
		return this.hidePatterns;
	}

	public BannerPattern.Patterns getPatterns() {
		return this.patterns;
	}

	public static class Serializer implements JsonSerializer<Banner> {
		@Override
		public void toJson(JsonObject json, @NotNull Banner banner, JsonSerializationContext context) {
			JsonArray patterns = new JsonArray();
			for (Pair<RegistryEntry<BannerPattern>, DyeColor> pair : banner.patterns.entries) {
				JsonObject patternJson = new JsonObject();

				RegistryEntry<BannerPattern> patternEntry = pair.getFirst();
				patternJson.addProperty(
					"pattern",
					patternEntry
						.getKey()
						.orElseThrow(() -> new JsonSyntaxException("Unknown pattern: " + patternEntry))
						.getValue()
						.toString()
				);
				patternJson.addProperty("color", pair.getSecond().getName());

				patterns.add(patternJson);
			}

			json.add("patterns", patterns);
			json.addProperty("hide_patterns_in_tooltip", banner.hidePatterns);

			Text name = banner.name;
			if (name != null) json.add("name", Text.Serializer.toJsonTree(name));

			json.addProperty("base", Registries.ITEM.getId(banner.base).toString());
		}

		@Override
		public Banner fromJson(JsonObject json, JsonDeserializationContext context) {
			BannerPattern.Patterns patterns = new BannerPattern.Patterns();
			JsonArray patternsArray = JsonHelper.getArray(json, "patterns");

			for (int i = 0; i < patternsArray.size(); i++) {
				JsonObject pattern = JsonHelper.asObject(patternsArray.get(i), "pattern[" + i + "]");
				Identifier patternId = new Identifier(JsonHelper.getString(pattern, "pattern"));

				RegistryEntry<BannerPattern> patternEntry = Registries.BANNER_PATTERN.getEntry(RegistryKey.of(
					RegistryKeys.BANNER_PATTERN,
					patternId
				)).orElse(null);
				if (patternEntry == null) throw new JsonSyntaxException("Unknown pattern: " + patternId);

				String colorName = JsonHelper.getString(pattern, "color");
				DyeColor color = DyeColor.byName(colorName, null);
				if (color == null) throw new JsonSyntaxException("Unknown color: " + colorName);

				patterns.add(Pair.of(patternEntry, color));
			}

			boolean hidePatterns = JsonHelper.getBoolean(json, "hide_patterns_in_tooltip");

			Text name = null;
			if (json.has("name")) name = Text.Serializer.fromJson(json.get("name"));

			Item base = Registries.ITEM.get(new Identifier(JsonHelper.getString(json, "base")));

			return new Banner(name, base, hidePatterns, patterns);
		}
	}
}
