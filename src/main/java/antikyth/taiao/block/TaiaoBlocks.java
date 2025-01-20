// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.feature.tree.sapling.CabbageTreeSaplingGenerator;
import antikyth.taiao.world.gen.feature.tree.sapling.KauriSaplingGenerator;
import antikyth.taiao.world.gen.feature.tree.sapling.MamakuSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TaiaoBlocks {
    public static final Block KAURI_SAPLING = new Builder(
            Taiao.id("kauri_sapling"),
            new SaplingBlock(new KauriSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.DARK_OAK_SAPLING))
    ).register(true);
    public static final Block POTTED_KAURI_SAPLING = new Builder(
            Taiao.id("potted_kauri_sapling"),
            Blocks.createFlowerPotBlock(KAURI_SAPLING)
    ).register(false);
    public static final Block KAURI_LEAVES = new Builder(
            Taiao.id("kauri_leaves"),
            Blocks.createLeavesBlock(BlockSoundGroup.GRASS)
    ).copyFlammable(Blocks.DARK_OAK_LEAVES).register(true);

    public static final Block STRIPPED_KAURI_LOG = new Builder(
            Taiao.id("stripped_kauri_log"),
            Blocks.createLogBlock(MapColor.OFF_WHITE, MapColor.OFF_WHITE)
    ).register(true);
    public static final Block KAURI_LOG = new Builder(
            Taiao.id("kauri_log"),
            Blocks.createLogBlock(MapColor.OFF_WHITE, MapColor.STONE_GRAY)
    ).strippable(STRIPPED_KAURI_LOG).register(true);
    public static final Block STRIPPED_KAURI_WOOD = new Builder(
            Taiao.id("stripped_kauri_wood"),
            Blocks.createLogBlock(MapColor.OFF_WHITE, MapColor.OFF_WHITE)
    ).register(true);
    public static final Block KAURI_WOOD = new Builder(
            Taiao.id("kauri_wood"),
            Blocks.createLogBlock(MapColor.STONE_GRAY, MapColor.STONE_GRAY)
    ).strippable(STRIPPED_KAURI_WOOD).register(true);

    public static final Block KAURI_PLANKS = new Builder(
            Taiao.id("kauri_planks"),
            createPlanks(MapColor.OFF_WHITE)
    ).copyFlammable(Blocks.OAK_PLANKS).register(true);
    public static final Block KAURI_PRESSURE_PLATE = new Builder(
            Taiao.id("kauri_pressure_plate"),
            createWoodenPressurePlate(KAURI_PLANKS, WoodFamily.KAURI.getBlockSetType())
    ).register(true);
    public static final Block KAURI_BUTTON = new Builder(
            Taiao.id("kauri_button"),
            Blocks.createWoodenButtonBlock(WoodFamily.KAURI.getBlockSetType())
    ).register(true);
    public static final Block KAURI_STAIRS = new Builder(
            Taiao.id("kauri_stairs"),
            new StairsBlock(KAURI_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(KAURI_PLANKS))
    ).copyFlammable(KAURI_PLANKS).register(true);
    public static final Block KAURI_SLAB = new Builder(
            Taiao.id("kauri_slab"),
            new SlabBlock(FabricBlockSettings.copyOf(KAURI_PLANKS))
    ).copyFlammable(KAURI_PLANKS).register(true);
    public static final Block KAURI_FENCE_GATE = new Builder(
            Taiao.id("kauri_fence_gate"),
            new FenceGateBlock(FabricBlockSettings.copyOf(KAURI_PLANKS), WoodFamily.KAURI.getWoodType())
    ).copyFlammable(KAURI_PLANKS).register(true);
    public static final Block KAURI_FENCE = new Builder(
            Taiao.id("kauri_fence"),
            new FenceBlock(FabricBlockSettings.copyOf(KAURI_PLANKS))
    ).copyFlammable(KAURI_PLANKS).register(true);

    // Tī kōuka foliage
    public static final Block CABBAGE_TREE_SAPLING = new Builder(
            Taiao.id("cabbage_tree_sapling"),
            new SaplingBlock(new CabbageTreeSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING))
    ).register(true);
    public static final Block POTTED_CABBAGE_TREE_SAPLING = new Builder(
            Taiao.id("potted_cabbage_tree_sapling"),
            Blocks.createFlowerPotBlock(CABBAGE_TREE_SAPLING)
    ).register(false);
    public static final Block CABBAGE_TREE_LEAVES = new Builder(
            Taiao.id("cabbage_tree_leaves"),
            new SlowMovementLeavesBlock(createLeavesSettings(MapColor.LIME, BlockSoundGroup.GRASS).noCollision())
    ).copyFlammable(Blocks.OAK_LEAVES).register(true);

    // Tī kōuka wood
    public static final Block STRIPPED_CABBAGE_TREE_LOG = new Builder(
            Taiao.id("stripped_cabbage_tree_log"),
            createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN)
    ).register(true);
    public static final Block CABBAGE_TREE_LOG = new Builder(
            Taiao.id("cabbage_tree_log"),
            createThinLogBlock(MapColor.OAK_TAN, MapColor.STONE_GRAY)
    ).register(true);
    public static final Block STRIPPED_CABBAGE_TREE_WOOD = new Builder(
            Taiao.id("stripped_cabbage_tree_wood"),
            createThinLogBlock(MapColor.OAK_TAN, MapColor.OAK_TAN)
    ).register(true);
    public static final Block CABBAGE_TREE_WOOD = new Builder(
            Taiao.id("cabbage_tree_wood"),
            createThinLogBlock(MapColor.STONE_GRAY, MapColor.STONE_GRAY)
    ).register(true);

    // Mamaku foliage
    public static final Block MAMAKU_SAPLING = new Builder(
            Taiao.id("mamaku_sapling"),
            new SaplingBlock(new MamakuSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING))
    ).register(true);
    public static final Block POTTED_MAMAKU_SAPLING = new Builder(
            Taiao.id("potted_mamaku_sapling"),
            Blocks.createFlowerPotBlock(MAMAKU_SAPLING)
    ).register(false);

    // Mamaku wood
    public static final Block MAMAKU_LOG = new Builder(
            Taiao.id("mamaku_log"),
            createThinLogBlock(MapColor.SPRUCE_BROWN, MapColor.SPRUCE_BROWN)
    ).register(true);
    public static final Block MAMAKU_WOOD = new Builder(
            Taiao.id("mamaku_wood"),
            createThinLogBlock(MapColor.SPRUCE_BROWN, MapColor.SPRUCE_BROWN)
    ).register(true);

    public static void initialize() {
        registerFlammableTagCopy(TaiaoBlockTags.KAURI_LOGS, Blocks.ACACIA_LOG);
        registerFlammableTagCopy(TaiaoBlockTags.CABBAGE_TREE_LOGS, Blocks.OAK_LOG);
        registerFlammableTagCopy(TaiaoBlockTags.MAMAKU_LOGS, Blocks.SPRUCE_LOG);
    }

    /**
     * A unified wood familyBuilder containing a {@link BlockFamily}, {@link BlockSetType}, and a {@link WoodType}.
     */
    public static class WoodFamily {
        public static final WoodFamily KAURI = register(
                Taiao.id("kauri"),
                () -> new BlockFamily.Builder(TaiaoBlocks.KAURI_PLANKS)
                        .button(TaiaoBlocks.KAURI_BUTTON)
                        .fence(TaiaoBlocks.KAURI_FENCE)
                        .fenceGate(TaiaoBlocks.KAURI_FENCE_GATE)
                        .pressurePlate(TaiaoBlocks.KAURI_PRESSURE_PLATE)
                        .slab(TaiaoBlocks.KAURI_SLAB)
                        .stairs(TaiaoBlocks.KAURI_STAIRS)
                        .group("wooden")
                        .unlockCriterionName("has_planks")
                        .build()
        );

        private final Supplier<BlockFamily> familyFactory;

        private BlockFamily family;
        private final BlockSetType blockSetType;
        private final WoodType woodType;

        protected WoodFamily(
                Supplier<BlockFamily> familyFactory,
                BlockSetType blockSetType,
                WoodType woodType
        ) {
            this.familyFactory = familyFactory;
            this.blockSetType = blockSetType;
            this.woodType = woodType;
        }

        /**
         * Creates and registers a wood family based on the given {@link BlockFamily}.
         * <p>
         * If you wish to customize the sounds, see
         * {@link WoodFamily#register(Identifier, Supplier, BlockSetTypeBuilder, WoodTypeBuilder)}.
         */
        public static @NotNull WoodFamily register(Identifier id, Supplier<BlockFamily> familyFactory) {
            return register(id, familyFactory, new BlockSetTypeBuilder(), new WoodTypeBuilder());
        }

        /**
         * Creates and registers a wood family based on the given {@link BlockFamily} with customized
         * {@link BlockSetType} and {@link WoodType}.
         */
        public static @NotNull WoodFamily register(
                Identifier id,
                Supplier<BlockFamily> familyFactory,
                @NotNull BlockSetTypeBuilder blockSetTypeBuilder,
                @NotNull WoodTypeBuilder woodTypeBuilder
        ) {
            BlockSetType blockSetType = blockSetTypeBuilder.register(id);
            WoodType woodType = woodTypeBuilder.register(id, blockSetType);

            return new WoodFamily(familyFactory, blockSetType, woodType);
        }

        public BlockFamily getBlockFamily() {
            if (this.family == null) this.family = this.familyFactory.get();

            return this.family;
        }

        public BlockSetType getBlockSetType() {
            return this.blockSetType;
        }

        public WoodType getWoodType() {
            return this.woodType;
        }
    }

    @Contract("_ -> new")
    public static @NotNull Block createPlanks(MapColor color) {
        return new Block(AbstractBlock.Settings.create()
                .mapColor(color)
                .instrument(Instrument.BASS)
                .strength(2.0F, 3.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable());
    }

    @Contract("_, _ -> new")
    public static @NotNull Block createWoodenPressurePlate(@NotNull Block planks, BlockSetType blockSetType) {
        return new PressurePlateBlock(
                PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.create()
                        .mapColor(planks.getDefaultMapColor())
                        .solid()
                        .instrument(Instrument.BASS)
                        .noCollision()
                        .strength(0.5f)
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY),
                blockSetType
        );
    }

    public static AbstractBlock.Settings createLeavesSettings(MapColor color, BlockSoundGroup soundGroup) {
        return AbstractBlock.Settings.create()
                .mapColor(color)
                .strength(0.2F)
                .ticksRandomly()
                .sounds(soundGroup)
                .nonOpaque()
                .allowsSpawning(Blocks::canSpawnOnLeaves)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never)
                .burnable()
                .pistonBehavior(PistonBehavior.DESTROY)
                .solidBlock(Blocks::never);
    }

    @Contract("_, _ -> new")
    public static @NotNull ThinLogBlock createThinLogBlock(
            MapColor end,
            MapColor side
    ) {
        return new ThinLogBlock(AbstractBlock.Settings.create()
                .mapColor(state -> state.get(ThinLogBlock.UP) ? end : side)
                .instrument(Instrument.BASS)
                .strength(2f)
                .sounds(BlockSoundGroup.WOOD)
                .burnable());
    }

    /**
     * Registers a whole {@code tag} as flammable.
     */
    public static void registerFlammableTag(TagKey<Block> tag, int burnChance, int spreadChance) {
        FlammableBlockRegistry.getDefaultInstance().add(tag, burnChance, spreadChance);
    }

    /**
     * Registers a whole {@code tag} as flammable, copying the {@code burnChance} and {@code spreadChance} from the
     * given {@code block}.
     */
    public static void registerFlammableTagCopy(TagKey<Block> tag, Block block) {
        FlammableBlockRegistry.getDefaultInstance().add(tag, FlammableBlockRegistry.getDefaultInstance().get(block));
    }

    /**
     * A builder for registering blocks.
     */
    public static class Builder {
        private final Identifier id;
        private final Block block;

        public Builder(Identifier id, Block block) {
            this.id = id;
            this.block = block;
        }

        /**
         * Makes this block flammable.
         * <p>
         * See {@link FireBlock#registerDefaultFlammables()} for a list of vanilla {@code burnChance} and
         * {@code spreadChance} examples.
         * <p>
         * Note: to make an entire tag flammable, call {@link FlammableBlockRegistry#add(TagKey, int, int)} from an
         * initialize method instead.
         *
         * @param burnChance   The chance for this block to burn.
         * @param spreadChance The chance for this block to spread fire to other blocks.
         */
        public Builder flammable(int burnChance, int spreadChance) {
            FlammableBlockRegistry.getDefaultInstance().add(this.block, burnChance, spreadChance);

            return this;
        }

        /**
         * Makes this block flammable, copying the {@code burnChance} and {@code spreadChance} from the given
         * {@code block}.
         * <p>
         * See {@link FireBlock#registerDefaultFlammables()} for a list of vanilla {@code burnChance} and
         * {@code spreadChance} examples.
         * <p>
         * Note: to make an entire tag flammable, call {@link FlammableBlockRegistry#add(TagKey, int, int)} from an
         * initialize method instead.
         */
        public Builder copyFlammable(Block block) {
            FlammableBlockRegistry.getDefaultInstance()
                    .add(this.block, FlammableBlockRegistry.getDefaultInstance().get(block));

            return this;
        }

        /**
         * Makes this block strippable into the given {@code stripped} form.
         * <p>
         * Warning: this only works for blocks with an {@link net.minecraft.state.property.Properties#AXIS AXIS}
         * property. For other blocks, use {@link Strippable#STRIPPED_BLOCKS}.
         */
        public Builder strippable(Block stripped) {
            StrippableBlockRegistry.register(this.block, stripped);

            return this;
        }

        /**
         * Registers this block, completing the builder.
         *
         * @param registerItem Whether to register a {@link BlockItem} for this block.
         */
        public Block register(boolean registerItem) {
            if (registerItem) {
                BlockItem item = new BlockItem(this.block, new Item.Settings());

                Registry.register(Registries.ITEM, this.id, item);
            }

            return Registry.register(Registries.BLOCK, this.id, this.block);
        }
    }
}
