// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.render.model.*;
import antikyth.taiao.entity.render.renderer.*;
import antikyth.taiao.item.TaiaoBoats;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

public class TaiaoEntityModels {
	public static final EntityModelLayer KIWI = registerModelLayer(
		TaiaoEntities.KIWI,
		"main",
		KiwiEntityModel::getTexturedModelData
	);
	public static final EntityModelLayer PUUKEKO = registerModelLayer(
		TaiaoEntities.PUUKEKO,
		"main",
		PuukekoEntityModel::getTexturedModelData
	);
	public static final EntityModelLayer MOA = registerModelLayer(
		TaiaoEntities.MOA,
		"main",
		MoaEntityModel::getTexturedModelData
	);
	public static final EntityModelLayer KAAKAAPOO = registerModelLayer(
		TaiaoEntities.KAAKAAPOO,
		"main",
		KaakaapooEntityModel::getTexturedModelData
	);
	public static final EntityModelLayer AUSTRALASIAN_BITTERN = registerModelLayer(
		TaiaoEntities.AUSTRALASIAN_BITTERN,
		"main",
		AustralasianBitternEntityModel::getTexturedModelData
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering entity renderers and model layers");

		TerraformBoatClientHelper.registerModelLayers(TaiaoBoats.KAURI.getValue(), false);
		TerraformBoatClientHelper.registerModelLayers(TaiaoBoats.KAHIKATEA.getValue(), false);
		TerraformBoatClientHelper.registerModelLayers(TaiaoBoats.RIMU.getValue(), false);
		TerraformBoatClientHelper.registerModelLayers(TaiaoBoats.MAMAKU.getValue(), true);

		EntityRendererRegistry.register(TaiaoEntities.KIWI, KiwiEntityRenderer::new);
		EntityRendererRegistry.register(TaiaoEntities.PUUKEKO, PuukekoEntityRenderer::new);
		EntityRendererRegistry.register(TaiaoEntities.MOA, MoaEntityRenderer::new);
		EntityRendererRegistry.register(TaiaoEntities.KAAKAAPOO, KaakaapooEntityRenderer::new);
		EntityRendererRegistry.register(TaiaoEntities.AUSTRALASIAN_BITTERN, AustralasianBitternEntityRenderer::new);
	}

	public static EntityModelLayer registerModelLayer(
		EntityType<?> entityType,
		String name,
		EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider
	) {
		return registerModelLayer(
			new EntityModelLayer(Registries.ENTITY_TYPE.getId(entityType), name),
			texturedModelDataProvider
		);
	}

	public static EntityModelLayer registerModelLayer(
		EntityModelLayer layer,
		EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider
	) {
		EntityModelLayerRegistry.registerModelLayer(layer, texturedModelDataProvider);

		return layer;
	}
}
