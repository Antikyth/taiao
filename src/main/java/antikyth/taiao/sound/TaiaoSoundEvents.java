// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.sound;

import antikyth.taiao.Taiao;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class TaiaoSoundEvents {
	/**
	 * {@linkplain antikyth.taiao.entity.TaiaoEntities#KIWI Kiwi} chirp sounds.
	 */
	public static final SoundEvent ENTITY_KIWI_AMBIENT = register(Taiao.id("entity.kiwi.ambient"));
	public static final SoundEvent ENTITY_KIWI_DEATH = register(Taiao.id("entity.kiwi.death"));
	public static final SoundEvent ENTITY_KIWI_HURT = register(Taiao.id("entity.kiwi.hurt"));

	/**
	 * {@linkplain antikyth.taiao.entity.TaiaoEntities#KAAKAAPOO Kākāpō} boom sounds.
	 */
	public static final SoundEvent ENTITY_KAAKAAPOO_AMBIENT = register(Taiao.id("entity.kaakaapoo.ambient"));
	public static final SoundEvent ENTITY_KAAKAAPOO_DEATH = register(Taiao.id("entity.kaakaapoo.death"));
	public static final SoundEvent ENTITY_KAAKAAPOO_HURT = register(Taiao.id("entity.kaakaapoo.hurt"));

	public static final SoundEvent ENTITY_PUUKEKO_BABY_AMBIENT = register(Taiao.id("entity.puukeko.baby.ambient"));
	public static final SoundEvent ENTITY_PUUKEKO_ADULT_AMBIENT = register(Taiao.id("entity.puukeko.adult.ambient"));
	public static final SoundEvent ENTITY_PUUKEKO_BABY_DEATH = register(Taiao.id("entity.puukeko.baby.death"));
	public static final SoundEvent ENTITY_PUUKEKO_ADULT_DEATH = register(Taiao.id("entity.puukeko.adult.death"));
	public static final SoundEvent ENTITY_PUUKEKO_BABY_HURT = register(Taiao.id("entity.puukeko.baby.hurt"));
	public static final SoundEvent ENTITY_PUUKEKO_ADULT_HURT = register(Taiao.id("entity.puukeko.adult.hurt"));

	public static final SoundEvent ENTITY_EEL_DEATH = register(Taiao.id("entity.eel.death"));
	public static final SoundEvent ENTITY_EEL_FLOP = register(Taiao.id("entity.eel.flop"));
	public static final SoundEvent ENTITY_EEL_HURT = register(Taiao.id("entity.eel.hurt"));

	public static final SoundEvent BLOCK_HARAKEKE_BREAK = register(Taiao.id("block.harakeke.break"));
	public static final SoundEvent BLOCK_HARAKEKE_STEP = register(Taiao.id("block.harakeke.step"));
	public static final SoundEvent BLOCK_HARAKEKE_PLACE = register(Taiao.id("block.harakeke.place"));
	public static final SoundEvent BLOCK_HARAKEKE_HIT = register(Taiao.id("block.harakeke.hit"));
	public static final SoundEvent BLOCK_HARAKEKE_FALL = register(Taiao.id("block.harakeke.fall"));
	public static final SoundEvent BLOCK_HARAKEKE_SHEAR = register(Taiao.id("block.harakeke.shear"));

	public static SoundEvent register(Identifier id) {
		return register(id, id);
	}

	public static SoundEvent register(Identifier id, Identifier soundId) {
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
	}

	public static void initialize() {
		Taiao.LOGGER.debug("Registering sound events");
	}
}
