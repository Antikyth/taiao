// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.predicate;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

/**
 * A predicate that matches for a {@link StatusEffect} if it matches any of the given
 * {@linkplain StatusEffect types}, {@linkplain StatusEffectCategory categories}, or
 * {@linkplain TagKey<StatusEffect> tags}.
 */
public class StatusEffectPredicate {
	public static final StatusEffectPredicate EMPTY = new StatusEffectPredicate(null, null, null);

	protected static final String TAGS_KEY = "tags";
	protected static final String CATEGORIES_KEY = "categories";
	protected static final String TYPES_KEY = "types";

	protected final @Nullable Set<TagKey<StatusEffect>> tags;
	protected final @Nullable Set<StatusEffectCategory> categories;
	protected final @Nullable Set<StatusEffect> types;

	StatusEffectPredicate(
		@Nullable Set<TagKey<StatusEffect>> tags,
		@Nullable Set<StatusEffectCategory> categories,
		@Nullable Set<StatusEffect> types
	) {
		this.tags = tags;
		this.categories = categories;
		this.types = types;
	}

	@Contract(" -> new")
	public static @NotNull Builder builder() {
		return new Builder();
	}

	public boolean test(StatusEffect effect) {
		if (this.categories != null && this.categories.contains(effect.getCategory())) {
			return true;
		}
		if (this.types != null && this.types.contains(effect)) {
			return true;
		}

		if (this.tags != null) {
			RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(effect);

			for (TagKey<StatusEffect> tag : this.tags) {
				if (entry.isIn(tag)) return true;
			}
		}

		return false;
	}

	public void writeJson(JsonObject json) {
		if (this.tags != null && !this.tags.isEmpty()) {
			JsonArray array = new JsonArray();

			for (TagKey<StatusEffect> tag : this.tags) {
				array.add(tag.id().toString());
			}

			json.add(TAGS_KEY, array);
		}

		if (this.categories != null && !this.categories.isEmpty()) {
			JsonArray array = new JsonArray();

			for (StatusEffectCategory category : this.categories) {
				array.add(categoryToJson(category));
			}

			json.add(CATEGORIES_KEY, array);
		}

		if (this.types != null && !this.types.isEmpty()) {
			JsonArray array = new JsonArray();

			for (StatusEffect type : this.types) {
				Identifier id = Registries.STATUS_EFFECT.getId(type);

				if (id != null) array.add(id.toString());
			}

			json.add(TYPES_KEY, array);
		}
	}

	public static StatusEffectPredicate fromJson(@Nullable JsonElement element) {
		if (element == null || element.isJsonNull()) return EMPTY;

		JsonObject json = element.getAsJsonObject();
		Set<TagKey<StatusEffect>> tags = null;
		Set<StatusEffectCategory> categories = null;
		Set<StatusEffect> types = null;

		if (json.has(TAGS_KEY)) {
			JsonArray array = json.getAsJsonArray(TAGS_KEY);
			ImmutableSet.Builder<TagKey<StatusEffect>> builder = ImmutableSet.builder();

			for (JsonElement tag : array) {
				builder.add(TagKey.of(RegistryKeys.STATUS_EFFECT, new Identifier(tag.getAsString())));
			}

			tags = builder.build();
		}

		if (json.has(CATEGORIES_KEY)) {
			JsonArray array = json.getAsJsonArray(CATEGORIES_KEY);
			ImmutableSet.Builder<StatusEffectCategory> builder = ImmutableSet.builder();

			for (JsonElement categoryElement : array) {
				StatusEffectCategory category = categoryFromJson(categoryElement);
				if (category != null) builder.add(category);
			}

			categories = builder.build();
		}

		if (json.has(TYPES_KEY)) {
			JsonArray array = json.getAsJsonArray(TYPES_KEY);
			ImmutableSet.Builder<StatusEffect> builder = ImmutableSet.builder();

			for (JsonElement typeElement : array) {
				StatusEffect type = Registries.STATUS_EFFECT.get(new Identifier(typeElement.getAsString()));
				if (type != null) builder.add(type);
			}

			types = builder.build();
		}

		return new StatusEffectPredicate(tags, categories, types);
	}

	@Contract(pure = true)
	protected static @NotNull JsonElement categoryToJson(@NotNull StatusEffectCategory category) {
		return switch (category) {
			case BENEFICIAL -> new JsonPrimitive("beneficial");
			case NEUTRAL -> new JsonPrimitive("neutral");
			case HARMFUL -> new JsonPrimitive("harmful");
		};
	}

	protected static @Nullable StatusEffectCategory categoryFromJson(JsonElement element) {
		if (element == null || element.isJsonNull()) return null;

		return switch (element.getAsString()) {
			case "beneficial" -> StatusEffectCategory.BENEFICIAL;
			case "neutral" -> StatusEffectCategory.NEUTRAL;
			case "harmful" -> StatusEffectCategory.HARMFUL;

			default -> null;
		};
	}

	public static class Builder {
		private final ImmutableSet.Builder<TagKey<StatusEffect>> tags = ImmutableSet.builder();
		private final ImmutableSet.Builder<StatusEffectCategory> categories = ImmutableSet.builder();
		private final ImmutableSet.Builder<StatusEffect> types = ImmutableSet.builder();

		@SafeVarargs
		public final Builder tags(TagKey<StatusEffect>... tags) {
			return this.tags(Arrays.asList(tags));
		}

		public Builder tags(Iterable<TagKey<StatusEffect>> tags) {
			this.tags.addAll(tags);
			return this;
		}

		public Builder categories(StatusEffectCategory... categories) {
			return this.categories(Arrays.asList(categories));
		}

		public Builder categories(Iterable<StatusEffectCategory> categories) {
			this.categories.addAll(categories);
			return this;
		}

		public Builder types(StatusEffect... types) {
			return this.types(Arrays.asList(types));
		}

		public Builder types(Iterable<StatusEffect> types) {
			this.types.addAll(types);
			return this;
		}

		public StatusEffectPredicate build() {
			return new StatusEffectPredicate(this.tags.build(), this.categories.build(), this.types.build());
		}
	}
}
