// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.entity.render.model.WakaEntityModel;
import antikyth.taiao.entity.waka.WakaType;
import antikyth.taiao.entity.waka.WakaTypeHolder;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Map;

public class WakaEntityRenderer<E extends BoatEntity & WakaTypeHolder> extends EntityRenderer<E> {
    private final Map<WakaType, Pair<Identifier, CompositeEntityModel<E>>> texturesAndModels;

    public WakaEntityRenderer(EntityRendererFactory.Context ctx, WakaType.ChestType chestType) {
        super(ctx);

        this.shadowRadius = 1.2f;

        this.texturesAndModels = WakaType.REGISTRY.getEntrySet().stream().collect(ImmutableMap.toImmutableMap(
                Map.Entry::getValue, entry -> {
                    boolean raft = entry.getValue().isRaft();

                    String prefix = "waka/";
                    switch (chestType) {
                        case NONE -> {
                            if (raft) prefix += "raft/";
                        }

                        case SINGLE -> {
                            prefix += "chest";

                            if (raft) prefix += "_raft";

                            prefix += "/";
                        }

                        case DOUBLE -> {
                            prefix += "double_chest";

                            if (raft) prefix += "_raft";

                            prefix += "/";
                        }
                    }

                    Identifier id = entry.getKey().getValue();
                    Identifier texturePath = new Identifier(
                            id.getNamespace(),
                            "textures/entity/" + prefix + id.getPath() + ".png"
                    );
                    Identifier modelId = new Identifier(id.getNamespace(), prefix + id.getPath());
                    EntityModelLayer layer = new EntityModelLayer(modelId, "main");

                    // FIXME
                    EntityModelLayerRegistry.registerModelLayer(layer, WakaEntityModel::getTexturedModelData);

                    CompositeEntityModel<E> model = createModel(ctx.getPart(layer), raft, chestType);

                    return new Pair<>(texturePath, model);
                }
        ));
    }

    @Override
    public Identifier getTexture(E entity) {
        WakaType type = entity.getWakaType();

        return this.texturesAndModels.get(type).getLeft();
    }

    private CompositeEntityModel<E> createModel(ModelPart root, boolean raft, WakaType.ChestType chestType) {
        return switch (chestType) {
            case NONE -> raft ? null : new WakaEntityModel<>(root);
            case SINGLE -> raft ? null : new WakaEntityModel<>(root);
            case DOUBLE -> raft ? null : new WakaEntityModel<>(root);
        };
    }
}
