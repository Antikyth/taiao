// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure.processor;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;

public class TaiaoStructureProcessorTypes {
	public static final StructureProcessorType<HiinakiBaitStructureProcessor> HIINAKI_BAIT = register(
		Taiao.id("hiinaki_bait"),
		HiinakiBaitStructureProcessor.CODEC
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered structure processor types");
	}

	public static <P extends StructureProcessor> StructureProcessorType<P> register(Identifier id, Codec<P> codec) {
		return Registry.register(Registries.STRUCTURE_PROCESSOR, id, () -> codec);
	}
}
