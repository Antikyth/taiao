// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement;

import antikyth.taiao.Taiao;
import antikyth.taiao.advancement.criteria.BlockPlacedFromKeteCriterion;
import antikyth.taiao.advancement.criteria.EntityFreedCriterion;
import antikyth.taiao.advancement.criteria.KeteChangedCriterion;
import antikyth.taiao.banner.TaiaoBanners;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.TaiaoStateProperties;
import antikyth.taiao.entity.damage.TaiaoDamageTypeTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.advancement.criterion.PlayerHurtEntityCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TaiaoAdvancements {
	public static final Map<Identifier, Advancement> ADVANCEMENTS = new HashMap<>();

	public static final Identifier MAIN_TAB = Taiao.id("main");

	public static final Identifier ROOT = rootBuilder(MAIN_TAB, TaiaoBanners.KAOKAO_TUPUNA_TUKUTUKU.getOrCreateStack())
		.criteriaMerger(CriterionMerger.OR)
		.criterion(
			"in_native_forest",
			TickCriterion.Conditions.createLocation(LocationPredicate.biome(TaiaoBiomes.NATIVE_FOREST))
		)
		.criterion(
			"in_native_forest",
			TickCriterion.Conditions.createLocation(LocationPredicate.biome(TaiaoBiomes.NATIVE_FOREST))
		)
		.build();

	public static final Identifier HARAKEKE = builder(MAIN_TAB, "harakeke", TaiaoBlocks.HARAKEKE)
		.criterion(
			"harakeke_harvested",
			ItemCriterion.Conditions.createItemUsedOnBlock(
				LocationPredicate.Builder.create()
					.block(
						BlockPredicate.Builder.create()
							.blocks(TaiaoBlocks.HARAKEKE)
							.state(
								StatePredicate.Builder.create()
									.exactMatch(TaiaoStateProperties.HARVESTABLE, true)
									.build()
							)
							.build()
					),
				ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEARS)
			)
		)
		.build();

	public static final Identifier BIGGER_ON_INSIDE = builder(MAIN_TAB, "bigger_on_inside", TaiaoItems.KETE)
		.parent(HARAKEKE)
		.criterion(
			"has_multiple_stacks_in_kete",
			KeteChangedCriterion.Conditions.create(NumberRange.IntRange.atLeast(2))
		)
		.build();
	public static final Identifier EFFICIENT_CONSTRUCTION = builder(MAIN_TAB, "efficient_construction", TaiaoItems.KETE)
		.parent(HARAKEKE)
		.criterion("block_placed_from_kete", BlockPlacedFromKeteCriterion.Conditions.create())
		.build();

	public static final Identifier TRAPPER = builder(MAIN_TAB, "trapper", TaiaoBlocks.HIINAKI)
		.criterion(
			"hurt_with_trap",
			PlayerHurtEntityCriterion.Conditions.create(
				DamagePredicate.Builder.create().type(
					DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(TaiaoDamageTypeTags.TRAPS))
				)
			)
		)
		.build();
	public static final Identifier FREEDOM = builder(MAIN_TAB, "freedom", TaiaoItems.EEL)
		.criterion("entity_freed", EntityFreedCriterion.Conditions.create())
		.build();

	public static Builder rootBuilder(Identifier tabId, ItemConvertible icon) {
		return rootBuilder(tabId, new ItemStack(icon));
	}

	public static Builder rootBuilder(Identifier tabId, ItemStack icon) {
		return builder(tabId, "root", icon).showToast(false).announceToChat(false).hasBackground(true).parent(null);
	}

	public static @NotNull Builder builder(
		Identifier tabId,
		String name,
		ItemConvertible icon
	) {
		return builder(tabId, name, new ItemStack(icon));
	}

	public static @NotNull Builder builder(
		Identifier tabId,
		String name,
		ItemStack icon
	) {
		return new Builder(tabId, name, icon);
	}

	public static String titleTranslationKey(@NotNull Identifier advancement) {
		return advancement.withPath(path -> path.replace('/', '.')).toTranslationKey("advancements", "title");
	}

	public static String descriptionTranslationKey(@NotNull Identifier advancement) {
		return advancement.withPath(path -> path.replace('/', '.')).toTranslationKey("advancements", "description");
	}

	public static class Builder {
		protected final Identifier tabId;
		protected final String name;
		protected final ItemStack icon;

		protected boolean showToast = true;
		protected boolean announceToChat = true;

		protected boolean hidden = false;
		protected boolean hasBackground = false;

		protected Map<String, AdvancementCriterion> criteria = new HashMap<>();
		protected AdvancementFrame frame = AdvancementFrame.TASK;
		protected @Nullable CriterionMerger merger;
		protected @Nullable Identifier parent;

		Builder(Identifier tabId, String name, ItemStack icon) {
			this.tabId = tabId;
			this.name = name;
			this.icon = icon;

			this.parent = this.tabId.withPath(path -> path + "/root");
		}

		public Builder showToast(boolean showToast) {
			this.showToast = showToast;
			return this;
		}

		public Builder announceToChat(boolean announceToChat) {
			this.announceToChat = announceToChat;
			return this;
		}

		public Builder criterion(String name, CriterionConditions conditions) {
			this.criteria.put(name, new AdvancementCriterion(conditions));
			return this;
		}

		public Builder frame(AdvancementFrame frame) {
			this.frame = frame;
			return this;
		}

		public Builder hidden(boolean hidden) {
			this.hidden = hidden;
			return this;
		}

		public Builder hasBackground(boolean hasBackground) {
			this.hasBackground = hasBackground;
			return this;
		}

		public Builder criteriaMerger(CriterionMerger merger) {
			this.merger = merger;
			return this;
		}

		public Builder parent(Identifier parent) {
			this.parent = parent;
			return this;
		}

		public Identifier build() {
			Identifier id = this.tabId.withPath(path -> path + "/" + this.name);

			Advancement.Builder builder = Advancement.Builder.create()
				.display(
					this.icon,
					Text.translatable(titleTranslationKey(id)),
					Text.translatable(descriptionTranslationKey(id)),
					this.hasBackground
						? this.tabId.withPath(path -> "textures/gui/advancements/backgrounds/" + path + ".png")
						: null,
					this.frame,
					this.showToast,
					this.announceToChat,
					this.hidden
				);

			builder.getCriteria().putAll(this.criteria);

			if (this.merger != null) builder.criteriaMerger(this.merger);

			builder.parent(this.parent);
			builder.findParent(ADVANCEMENTS::get);

			Advancement advancement = builder.build(id);
			ADVANCEMENTS.put(id, advancement);

			return id;
		}
	}
}
