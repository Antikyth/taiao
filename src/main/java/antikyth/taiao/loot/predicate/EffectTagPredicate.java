// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.predicate;

import antikyth.taiao.effect.TaiaoStatusEffectTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffectTagPredicate {
	public static final EffectTagPredicate EMPTY = new EffectTagPredicate();

	protected final TagKey<StatusEffect>[] tags;

	@SafeVarargs
	EffectTagPredicate(TagKey<StatusEffect>... tags) {
		this.tags = tags;
	}

	@Contract(value = "_ -> new", pure = true)
	@SafeVarargs
	public static @NotNull EffectTagPredicate of(TagKey<StatusEffect>... tags) {
		return new EffectTagPredicate(tags);
	}

	public boolean test(StatusEffect effect) {
		RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(effect);

		for (TagKey<StatusEffect> tag : this.tags) {
			if (entry.isIn(tag)) return true;
		}

		return false;
	}

	public void writeJson(JsonObject json) {
		if (this.tags.length == 0) return;

		if (this.tags.length == 1) {
			json.addProperty("tag", this.tags[0].id().toString());
		} else {
			JsonArray array = new JsonArray();

			for (TagKey<StatusEffect> tag : this.tags) {
				array.add(tag.id().toString());
			}

			json.add("tags", array);
		}
	}

	@Contract("_ -> new")
	@SuppressWarnings("unchecked")
	public static @NotNull EffectTagPredicate fromJson(@Nullable JsonObject json) {
		if (json == null) return EffectTagPredicate.EMPTY;

		JsonElement tag = json.get("tag");
		if (tag != null) {
			return new EffectTagPredicate(TaiaoStatusEffectTags.tagKey(new Identifier(tag.getAsString())));
		} else {
			JsonArray array = JsonHelper.asArray(json.get("tags"), "tags");
			TagKey<StatusEffect>[] tagArray = new TagKey[array.size()];

			for (int i = 0; i < tagArray.length; i++) {
				tagArray[i] = TaiaoStatusEffectTags.tagKey(new Identifier(array.get(i).getAsString()));
			}

			return new EffectTagPredicate(tagArray);
		}
	}
}
