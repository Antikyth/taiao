// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanPredicate {
	public static final BooleanPredicate ANY = new BooleanPredicate(null);
	public static final BooleanPredicate EXPECTS_TRUE = new BooleanPredicate(true);
	public static final BooleanPredicate EXPECTS_FALSE = new BooleanPredicate(false);

	protected final @Nullable Boolean expectedValue;

	BooleanPredicate(@Nullable Boolean expectedValue) {
		this.expectedValue = expectedValue;
	}

	@Contract(value = "_ -> new", pure = true)
	public static @NotNull BooleanPredicate of(@Nullable Boolean expectedValue) {
		return new BooleanPredicate(expectedValue);
	}

	public boolean test(boolean value) {
		return this.expectedValue == null || this.expectedValue == value;
	}

	public JsonElement toJson() {
		if (this.expectedValue == null) return JsonNull.INSTANCE;
		else return new JsonPrimitive(this.expectedValue);
	}

	@Contract("null -> new")
	public static @NotNull BooleanPredicate fromJson(@Nullable JsonElement element) {
		if (element == null || element.isJsonNull()) return new BooleanPredicate(null);
		else return new BooleanPredicate(element.getAsBoolean());
	}
}
