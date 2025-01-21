// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.foliage;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public abstract class ThreeDimensionalFoliagePlacer extends FoliagePlacer {
    public ThreeDimensionalFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    protected boolean isPositionValid3d(int dx, int dy, int dz, int radius, boolean giantTrunk) {
        if (giantTrunk) {
            dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
            dy = Math.min(Math.abs(dy), Math.abs(dy - 1));
            dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
        } else {
            dx = Math.abs(dx);
            dy = Math.abs(dy);
            dz = Math.abs(dz);
        }

        return isValidForLeaves3d(dx, dy, dz, radius);
    }

    protected abstract boolean isValidForLeaves3d(int dx, int dy, int dz, int radius);

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
