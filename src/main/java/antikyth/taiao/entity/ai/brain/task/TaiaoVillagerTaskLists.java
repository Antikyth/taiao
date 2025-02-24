// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TaiaoVillagerTaskLists {
	@Contract("_, _ -> new")
	public static ImmutableList.@NotNull Builder<Pair<? extends Task<? super VillagerEntity>, Integer>> createRandomWorkTasks(
		VillagerProfession profession,
		float speed
	) {
		return ImmutableList.<Pair<? extends Task<? super VillagerEntity>, Integer>>builder()
			.add(Pair.of(new HarvestHarakekeTask(speed), profession == VillagerProfession.SHEPHERD ? 2 : 5));
	}
}
