// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import com.bawnorton.configurable.Configurable;

public class TaiaoConfig {
	@Configurable
	public static class SpawnsAndGeneration {
		/**
		 * Whether to prevent cats from spawning in native Aotearoa New Zealand biomes.
		 */
		@Configurable
		public static boolean disableCatSpawnsInNativeBiomes = true;
		/**
		 * Whether to spawn eels (Māori: tuna) in vanilla or modded biomes, such as rivers.
		 */
		@Configurable
		public static boolean addEelsToVanillaBiomes = true;
		/**
		 * Whether to generate eel traps (Māori: hīnaki) in vanilla or modded biomes, such as rivers.
		 */
		@Configurable
		public static boolean addEelTrapsToVanillaBiomes = true;
	}

	@Configurable
	public static class AnimalBehavior {
		/**
		 * Whether mammalian predators like cats, wolves, and foxes should hunt various native Aotearoa New Zealand animals.
		 */
		@Configurable
		public static boolean mammalianPredatorsHuntNativeAnimals = true;
		/**
		 * Whether pūkeko should attack untamed predators that get near their chicks.
		 */
		@Configurable
		public static boolean puukekoAttackPredatorsNearChicks = true;
	}
}
