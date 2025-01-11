package antikyth.taiao.datagen;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.ThinLogBlock;
import com.google.gson.JsonElement;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TaiaoModels {
    public static final Model THIN_LOG_NOSIDE = block("thin_log_noside", "_noside", TextureKey.SIDE);
    public static final Model THIN_LOG_SIDE = block("thin_log_side", "_side", TextureKey.SIDE, TextureKey.END);

    public static final Model THIN_LOG_INVENTORY = item("thin_log", TextureKey.SIDE, TextureKey.END);

    public static TextureMap thinLogTextures(ThinLogBlock block, Optional<Identifier> side, Optional<Identifier> end) {
        Identifier sideId = side.orElse(TextureMap.getId(block));
        Identifier endId = end.orElse(TextureMap.getSubId(block, "_top"));

        return TextureMap.sideEnd(sideId, endId);
    }

    public static Model block(String name, String variant, TextureKey... textureKeys) {
        Identifier id = new Identifier(Taiao.MOD_ID, "block/" + name);

        return new Model(Optional.of(id), Optional.of(variant), textureKeys);
    }

    public static Model block(String name, TextureKey... textureKeys) {
        Identifier id = new Identifier(Taiao.MOD_ID, "block/" + name);

        return new Model(Optional.of(id), Optional.empty(), textureKeys);
    }

    public static Model item(String name, TextureKey... textureKeys) {
        Identifier id = new Identifier(Taiao.MOD_ID, "item/" + name);

        return new Model(Optional.of(id), Optional.empty(), textureKeys);
    }

    public static Identifier uploadItem(Model model, Item item, TextureMap textures, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
        return model.upload(ModelIds.getItemModelId(item), textures, modelCollector);
    }
}
