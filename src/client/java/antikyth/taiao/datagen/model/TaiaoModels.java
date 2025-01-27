// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.model;

import antikyth.taiao.Taiao;
import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TaiaoModels {
    public static final TextureKey OVERLAY_KEY = TextureKey.of("overlay");

    public static final Model THIN_LOG_NOSIDE = block("thin_log_noside", "_noside", TextureKey.SIDE);
    public static final Model THIN_LOG_SIDE = block("thin_log_side", "_side", TextureKey.SIDE, TextureKey.END);
    public static final Model FRUIT_LEAVES = block("fruit_leaves", "_fruit", TextureKey.ALL, OVERLAY_KEY);

    public static final Model THIN_LOG_INVENTORY = item("thin_log", TextureKey.SIDE, TextureKey.END);
    public static final Model SPAWN_EGG = vanillaItem("template_spawn_egg");

    public static TextureMap fruitLeavesTextures(
            Block block,
            @Nullable Identifier leaves,
            @Nullable Identifier overlay
    ) {
        leaves = leaves == null ? TextureMap.getId(block) : leaves;
        overlay = overlay == null ? TextureMap.getSubId(block, "_fruit") : overlay;

        return TextureMap.all(leaves).put(OVERLAY_KEY, overlay);
    }

    public static TextureMap thinLogTextures(Block block) {
        return thinLogTextures(block, null, null);
    }

    public static TextureMap thinWoodTextures(Block block) {
        return thinWoodTextures(block, null);
    }

    public static TextureMap thinLogTextures(Block block, @Nullable Identifier side, @Nullable Identifier end) {
        side = side == null ? TextureMap.getId(block) : side;
        end = end == null ? TextureMap.getSubId(block, "_top") : end;

        return TextureMap.sideEnd(side, end);
    }

    public static TextureMap thinWoodTextures(Block block, @Nullable Identifier side) {
        side = side == null ? TextureMap.getId(block) : side;

        return TextureMap.sideEnd(side, side);
    }

    public static @NotNull Model block(String name, String variant, TextureKey... textureKeys) {
        Identifier id = Taiao.id("block/" + name);

        return new Model(Optional.of(id), Optional.of(variant), textureKeys);
    }

    public static @NotNull Model block(String name, TextureKey... textureKeys) {
        Identifier id = Taiao.id("block/" + name);

        return new Model(Optional.of(id), Optional.empty(), textureKeys);
    }

    public static @NotNull Model vanillaItem(String name, TextureKey... textureKeys) {
        Identifier id = new Identifier("minecraft:item/" + name);

        return new Model(Optional.of(id), Optional.empty(), textureKeys);
    }

    public static @NotNull Model item(String name, TextureKey... textureKeys) {
        Identifier id = Taiao.id("item/" + name);

        return new Model(Optional.of(id), Optional.empty(), textureKeys);
    }

    public static Identifier uploadItem(
            @NotNull Model model,
            Item item,
            TextureMap textures,
            BiConsumer<Identifier, Supplier<JsonElement>> modelCollector
    ) {
        return model.upload(ModelIds.getItemModelId(item), textures, modelCollector);
    }
}
