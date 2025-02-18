// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class EmptyEntityTypeProvider extends EntityTypeProvider {
	public static final EmptyEntityTypeProvider INSTANCE = new EmptyEntityTypeProvider();

	public static final Codec<EmptyEntityTypeProvider> CODEC = Codec.unit(INSTANCE);

	@Override
	protected Type<?> getType() {
		return TaiaoEntityTypeProviderTypes.NONE;
	}

	@Override
	public @Nullable EntityType<?> get(Random random) {
		return null;
	}
}
