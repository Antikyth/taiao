// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class TaiaoBoats {
    public static final RegistryKey<TerraformBoatType> KAURI = register(
            Taiao.id("kauri"),
            new TaiaoBoatType(
                    false,
                    () -> TaiaoItems.KAURI_BOAT,
                    () -> TaiaoItems.KAURI_CHEST_BOAT,
                    () -> TaiaoBlocks.KAURI_PLANKS
            )
    );
    public static final RegistryKey<TerraformBoatType> KAHIKATEA = register(
            Taiao.id("kahikatea"),
            new TaiaoBoatType(
                    false,
                    () -> TaiaoItems.KAHIKATEA_BOAT,
                    () -> TaiaoItems.KAHIKATEA_CHEST_BOAT,
                    () -> TaiaoBlocks.KAHIKATEA_PLANKS
            )
    );
    public static final RegistryKey<TerraformBoatType> RIMU = register(
            Taiao.id("rimu"),
            new TaiaoBoatType(
                    false,
                    () -> TaiaoItems.RIMU_BOAT,
                    () -> TaiaoItems.RIMU_CHEST_BOAT,
                    () -> TaiaoBlocks.RIMU_PLANKS
            )
    );
    public static final RegistryKey<TerraformBoatType> MAMAKU = register(
            Taiao.id("mamaku"),
            new TaiaoBoatType(
                    true,
                    () -> TaiaoItems.MAMAKU_RAFT,
                    () -> TaiaoItems.MAMAKU_CHEST_RAFT,
                    () -> TaiaoBlocks.MAMAKU_PLANKS
            )
    );

    public static void initialize() {
        Taiao.LOGGER.debug("Registering Terraform boat types");
    }

    public static RegistryKey<TerraformBoatType> register(Identifier id, TerraformBoatType boatType) {
        RegistryKey<TerraformBoatType> key = TerraformBoatTypeRegistry.createKey(id);

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, id, boatType);

        return key;
    }
}
