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
    public static final SoundEvent ENTITY_KIWI_CHIRP = register(Taiao.id("entity.kiwi.chirp"));

    /**
     * {@linkplain antikyth.taiao.entity.TaiaoEntities#KAAKAAPOO Kākāpō} ching sounds.
     */
    public static final SoundEvent ENTITY_KAAKAAPOO_CHING = register(Taiao.id("entity.kaakaapoo.ching"));
    /**
     * {@linkplain antikyth.taiao.entity.TaiaoEntities#KAAKAAPOO Kākāpō} boom sounds.
     */
    public static final SoundEvent ENTITY_KAAKAAPOO_BOOM = register(Taiao.id("entity.kaakaapoo.boom"));

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
