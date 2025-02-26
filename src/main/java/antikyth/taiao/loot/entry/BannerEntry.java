// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.entry;

import antikyth.taiao.banner.Banner;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BannerEntry extends LeafEntry {
	final Banner banner;

	protected BannerEntry(
		Banner banner,
		int weight,
		int quality,
		LootCondition[] conditions,
		LootFunction[] functions
	) {
		super(weight, quality, conditions, functions);

		this.banner = banner;
	}

	@Override
	public LootPoolEntryType getType() {
		return TaiaoLootPoolEntryTypes.BANNER;
	}

	@Override
	protected void generateLoot(@NotNull Consumer<ItemStack> lootConsumer, LootContext context) {
		lootConsumer.accept(this.banner.getOrCreateStack());
	}

	@Contract(value = "_ -> new", pure = true)
	public static LeafEntry.@NotNull Builder<?> builder(Banner banner) {
		return builder((weight, quality, conditions, functions) -> new BannerEntry(
			banner,
			weight,
			quality,
			conditions,
			functions
		));
	}

	public static class Serializer extends LeafEntry.Serializer<BannerEntry> {
		protected final Banner.Serializer bannerSerializer = new Banner.Serializer();

		@Override
		public void addEntryFields(
			JsonObject json,
			BannerEntry bannerEntry,
			JsonSerializationContext context
		) {
			super.addEntryFields(json, bannerEntry, context);

			bannerSerializer.toJson(json, bannerEntry.banner, context);
		}

		@Override
		protected BannerEntry fromJson(
			JsonObject json,
			JsonDeserializationContext context,
			int weight,
			int quality,
			LootCondition[] conditions,
			LootFunction[] functions
		) {
			Banner banner = this.bannerSerializer.fromJson(json, context);

			return new BannerEntry(banner, weight, quality, conditions, functions);
		}
	}
}
