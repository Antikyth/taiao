// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement;

import antikyth.taiao.Taiao;
import antikyth.taiao.advancement.criteria.BlockPlacedFromKeteCriterion;
import antikyth.taiao.advancement.criteria.KeteStackCountCriterion;
import antikyth.taiao.advancement.criteria.TrapDestroyedCriterion;
import antikyth.taiao.advancement.criteria.predicate.BooleanPredicate;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.TaiaoStateProperties;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.damage.TaiaoDamageTypeTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.NumberRange.IntRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TaiaoAdvancements {
	public static final Map<Identifier, Advancement> ADVANCEMENTS = new HashMap<>();

	public static final Identifier MAIN_TAB = Taiao.id("main");

	public static final Identifier ROOT = rootBuilder(MAIN_TAB, Taiao.MOD_ICON.get())
		.criteriaMerger(CriterionMerger.OR)
		.criterion(
			"in_native_forest",
			TickCriterion.Conditions.createLocation(LocationPredicate.biome(TaiaoBiomes.NATIVE_FOREST))
		)
		.criterion(
			"in_kahikatea_swamp",
			TickCriterion.Conditions.createLocation(LocationPredicate.biome(TaiaoBiomes.KAHIKATEA_SWAMP))
		)
		.build();

	public static final Identifier BOOM_BOOM = builder(MAIN_TAB, "boom_boom", TaiaoItems.CONIFER_FRUIT)
		.parent(ROOT)
		.criterion(
			"tame_kaakaapoo",
			TameAnimalCriterion.Conditions.create(
				EntityPredicate.Builder.create().type(TaiaoEntities.KAAKAAPOO).build()
			)
		)
		.build();

	public static final Identifier HARAKEKE = builder(MAIN_TAB, "harakeke", TaiaoBlocks.HARAKEKE)
		.parent(ROOT)
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
	public static final Identifier BIGGER_ON_THE_INSIDE = builder(MAIN_TAB, "bigger_on_inside", TaiaoItems.KETE)
		.parent(HARAKEKE)
		.criterion(
			"has_multiple_stacks_in_kete",
			KeteStackCountCriterion.Conditions.create(IntRange.atLeast(2))
		)
		.build();
	public static final Identifier EFFICIENT_CONSTRUCTION = builder(MAIN_TAB, "efficient_construction", TaiaoItems.KETE)
		.parent(HARAKEKE)
		.criterion("block_placed_from_kete", BlockPlacedFromKeteCriterion.Conditions.create())
		.build();

	public static final Identifier SIT_BACK_AND_RELAX = builder(MAIN_TAB, "sit_back_and_relax", TaiaoBlocks.HIINAKI)
		.parent(ROOT)
		.criterion(
			"hurt_with_trap",
			PlayerHurtEntityCriterion.Conditions.create(
				DamagePredicate.Builder.create().type(
					DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(TaiaoDamageTypeTags.TRAPS))
				)
			)
		)
		.build();
	public static final Identifier A_KIND_HEART = builder(MAIN_TAB, "kind_heart", TaiaoItems.EEL)
		.parent(ROOT)
		.criterion(
			"entity_released",
			TrapDestroyedCriterion.Conditions.create(
				// Hīnaki
				LootContextPredicate.create(
					LocationCheckLootCondition.builder(
						LocationPredicate.Builder.create().block(
							BlockPredicate.Builder.create()
								.blocks(TaiaoBlocks.HIINAKI)
								.build()
						)
					).build()
				),
				// Has trapped entity
				EntityPredicate.asLootContextPredicate(EntityPredicate.Builder.create().build()),
				ItemPredicate.ANY,
				// Entity is released
				BooleanPredicate.EXPECTS_TRUE
			)
		)
		.build();
	public static final Identifier A_LITTLE_TRIP = builder(
		MAIN_TAB,
		"a_little_trip",
		TaiaoBlocks.HIINAKI
	)
		.parent(ROOT)
		.criterion(
			"entity_not_released",
			TrapDestroyedCriterion.Conditions.create(
				// Hīnaki
				LootContextPredicate.create(
					LocationCheckLootCondition.builder(
						LocationPredicate.Builder.create().block(
							BlockPredicate.Builder.create()
								.blocks(TaiaoBlocks.HIINAKI)
								.build()
						)
					).build()
				),
				// Has trapped entity
				EntityPredicate.asLootContextPredicate(EntityPredicate.Builder.create().build()),
				ItemPredicate.ANY,
				// Entity isn't released
				BooleanPredicate.EXPECTS_FALSE
			)
		)
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
		protected boolean hasBackground = true;

		protected Map<String, AdvancementCriterion> criteria = new HashMap<>();
		protected AdvancementFrame frame = AdvancementFrame.TASK;
		protected @Nullable CriterionMerger merger;
		protected @Nullable Identifier parent;

		Builder(Identifier tabId, String name, ItemStack icon) {
			this.tabId = tabId;
			this.name = name;
			this.icon = icon;
		}

		/**
		 * Applies {@code changes} to the builder.
		 */
		public Builder apply(@NotNull Consumer<Builder> changes) {
			changes.accept(this);
			return this;
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

		/**
		 * Sets the advancement's parent.
		 * <p>
		 * If the parent is non-null, the advancement's background is disabled, as it is not a root
		 * advancement.
		 */
		public Builder parent(Identifier parent) {
			this.parent = parent;
			this.hasBackground = parent == null;

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
