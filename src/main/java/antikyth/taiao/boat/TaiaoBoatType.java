// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.boat;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TaiaoBoatType implements TerraformBoatType {
	protected final boolean raft;
	protected final Supplier<@NotNull ItemConvertible> boatItemSupplier;
	protected final Supplier<@NotNull ItemConvertible> chestBoatItemSupplier;
	protected final Supplier<@NotNull ItemConvertible> planksSupplier;

	public TaiaoBoatType(
		boolean raft,
		Supplier<@NotNull ItemConvertible> boatItemSupplier,
		Supplier<@NotNull ItemConvertible> chestBoatItemSupplier,
		Supplier<@NotNull ItemConvertible> planksSupplier
	) {
		this.raft = raft;
		this.boatItemSupplier = boatItemSupplier;
		this.chestBoatItemSupplier = chestBoatItemSupplier;
		this.planksSupplier = planksSupplier;
	}

	@Override
	public boolean isRaft() {
		return this.raft;
	}

	@Override
	public Item getItem() {
		return this.boatItemSupplier.get().asItem();
	}

	@Override
	public Item getChestItem() {
		return this.chestBoatItemSupplier.get().asItem();
	}

	@Override
	public Item getPlanks() {
		return this.planksSupplier.get().asItem();
	}
}
