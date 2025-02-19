// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.client;

import antikyth.taiao.entity.villager.TaiaoVillagerTypes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerClothingFeatureRenderer.class)
public class VillagerClothingFeatureRendererMixin {
	/**
	 * Forces {@linkplain TaiaoVillagerTypes#MAAORI Māori villagers}' "hats" (faces) to always render.
	 * <p>
	 * Vanilla assumes that villager face textures specific to a certain {@link VillagerType} are hats, and so they
	 * should be hidden when a {@linkplain VillagerProfession profession} puts its own hat on top. In this case,
	 * however, the face textures are not hats, they are tā moko - tattoos.
	 */
	@WrapOperation(
		method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/model/ModelWithHat;setHatVisible(Z)V",
			ordinal = 0
		)
	)
	public void alwaysRenderMaaoriVillagerFaces(
		ModelWithHat model,
		boolean hatVisible,
		@NotNull Operation<Void> setHatVisible,
		@Local(ordinal = 0) VillagerType villagerType
	) {
		setHatVisible.call(model, hatVisible || villagerType.equals(TaiaoVillagerTypes.MAAORI));
	}
}
