// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class TaiaoEntityPredicates {
	/**
	 * Whether the entity is not a {@linkplain TameableEntity#isTamed() tamed} {@link TameableEntity}.
	 */
	public static final Predicate<LivingEntity> UNTAMED = entity -> !(entity instanceof TameableEntity tameable) || !tameable.isTamed();

	/**
	 * Whether the {@linkplain EntityType entity's type} belongs to the given tag.
	 */
	@Contract(pure = true)
	public static @NotNull Predicate<LivingEntity> isIn(TagKey<EntityType<?>> entityTypeTag) {
		return entity -> entity.getType().isIn(entityTypeTag);
	}
}
