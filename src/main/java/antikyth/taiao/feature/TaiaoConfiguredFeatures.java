package antikyth.taiao.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.ThinLogBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TaiaoConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> CABBAGE_TREE = Taiao.createRegistryKey(
            "cabbage_tree",
            RegistryKeys.CONFIGURED_FEATURE
    );

    public static void bootstrapConfiguredFeatures(@NotNull Registerable<ConfiguredFeature<?, ?>> registerable) {
        registerable.register(
                TaiaoConfiguredFeatures.CABBAGE_TREE,
                new ConfiguredFeature<>(
                        Feature.TREE,
                        createTree(
                                BlockStateProvider.of(((ThinLogBlock) TaiaoBlocks.CABBAGE_TREE_LOG).getVerticalState()),
                                BlockStateProvider.of(TaiaoBlocks.CABBAGE_TREE_LEAVES),
                                4,
                                2,
                                0,
                                2
                        ).ignoreVines().build()
                )
        );
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static TreeFeatureConfig.@NotNull Builder createTree(
            BlockStateProvider log,
            BlockStateProvider leaves,
            int baseHeight,
            int firstRandomHeight,
            int secondRandomHeight,
            int radius
    ) {
        return new TreeFeatureConfig.Builder(
                log,
                new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
                leaves,
                new BlobFoliagePlacer(
                        ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        );
    }
}
