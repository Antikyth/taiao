// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.advancement.criteria.TaiaoCriteria;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(
		World world,
		BlockPos pos,
		float yaw,
		GameProfile gameProfile
	) {
		super(world, pos, yaw, gameProfile);
	}

	/**
	 * Triggers {@link TaiaoCriteria#ITEM_CRAFTED} when a player crafts a recipe.
	 */
	@Inject(method = "onRecipeCrafted", at = @At("HEAD"))
	public void triggerItemCraftedCriterion(Recipe<?> recipe, List<ItemStack> ingredients, CallbackInfo ci) {
		TaiaoCriteria.ITEM_CRAFTED.trigger((ServerPlayerEntity) (Object) this, recipe);
	}
}
