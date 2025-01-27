// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.sapling;

import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class KahikateaSaplingGenerator extends LargeTreeSaplingGenerator {
    @Override
    protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return TaiaoConfiguredFeatures.YOUNG_KAHIKATEA_TREE;
    }

    @Override
    protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return TaiaoConfiguredFeatures.OLD_KAHIKATEA_TREE;
    }
}
