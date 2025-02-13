// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TallBlockEmiStack extends EmiStack {
	protected static final MinecraftClient CLIENT = MinecraftClient.getInstance();

	protected final Block block;
	protected final LinkedHashMap<BlockPos, BlockState> states;

	protected Vector3f center;
	protected float scale = 1f;
	@Nullable
	protected BlockState describeSingleState = null;
	protected List<Property<?>> hiddenProperties = List.of();

	protected static final Transformation TRANSFORMATION = new Transformation(
		new Vector3f(30f, 210f, 0f),
		new Vector3f(),
		new Vector3f(0.625f)
	);

	public TallBlockEmiStack(
		Block block,
		LinkedHashMap<BlockPos, BlockState> states
	) {
		this(block, states, new Vector3f());

		// Determine 'center of mass'
		BlockPos.Mutable sum = new BlockPos.Mutable();

		for (BlockPos pos : states.keySet()) {
			sum.set(sum, pos);
		}

		this.center = new Vector3f(
			(float) sum.getX() / (float) states.size(),
			(float) sum.getY() / (float) states.size(),
			(float) sum.getZ() / (float) states.size()
		);
	}

	public TallBlockEmiStack(Block block, LinkedHashMap<BlockPos, BlockState> states, Vector3f center) {
		this.block = block;
		this.states = states;
		this.center = center;
	}

	public TallBlockEmiStack scale(float scale) {
		this.scale = scale;
		return this;
	}

	/**
	 * A single {@link BlockState} to describe in the tooltip.
	 * <p>
	 * If {@code null} (the default), all states will be described.
	 */
	public TallBlockEmiStack describeSingleState(@Nullable BlockState state) {
		this.describeSingleState = state;
		return this;
	}

	/**
	 * {@link BlockState} properties to hide in the tooltip.
	 */
	public TallBlockEmiStack hiddenProperties(Property<?>... hiddenProperties) {
		return this.hiddenProperties(List.of(hiddenProperties));
	}

	/**
	 * {@link BlockState} properties to hide in the tooltip.
	 */
	public TallBlockEmiStack hiddenProperties(List<Property<?>> hiddenProperties) {
		this.hiddenProperties = hiddenProperties;
		return this;
	}

	@Override
	public EmiStack copy() {
		return new TallBlockEmiStack(this.block, this.states, this.center)
			.scale(this.scale)
			.describeSingleState(this.describeSingleState)
			.hiddenProperties(this.hiddenProperties);
	}

	@Override
	public void render(@NotNull DrawContext draw, int x, int y, float delta, int flags) {
		MatrixStack matrices = draw.getMatrices();

		matrices.push();

		// Transforms, the same applied to items when rendering in GUIs
		matrices.translate(x + 8f, y + 8f, 150);
		matrices.multiplyPositionMatrix(new Matrix4f().scaling(1f, -1f, 1f));
		matrices.scale(16f, 16f, 16f);

		MinecraftClient client = MinecraftClient.getInstance();
		World world = client.world;
		ClientPlayerEntity player = client.player;
		BlockPos origin = player != null ? player.getBlockPos() : BlockPos.ORIGIN;

		BlockRenderManager blockRenderManager = client.getBlockRenderManager();
		VertexConsumerProvider consumers = draw.getVertexConsumers();
		VertexConsumer consumer = consumers.getBuffer(RenderLayers.getEntityBlockLayer(
			this.block.getDefaultState(),
			true
		));

		Random random = Random.create(42L);
		consumer.light(LightmapTextureManager.MAX_LIGHT_COORDINATE);

		// Apply transforms
		TRANSFORMATION.apply(false, matrices);
		matrices.scale(this.scale, this.scale, this.scale);
		// Items are shifted like this before being rendered
		matrices.translate(-0.5f, -0.5f, -0.5f);
		// Adjust the center of the multiblock
		matrices.translate(-this.center.x, -this.center.y, -this.center.z);

		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Map.Entry<BlockPos, BlockState> part : this.states.entrySet()) {
			BlockPos pos = part.getKey();
			BlockState state = part.getValue();

			mutable.set(origin, pos);

			// Render
			matrices.push();

			matrices.translate(pos.getX(), pos.getY(), pos.getZ());
			blockRenderManager.renderBlock(state, mutable, world, matrices, consumer, false, random);

			matrices.pop();
		}

		matrices.pop();

		draw.draw();
	}

	@Override
	public boolean isEmpty() {
		return this.states.isEmpty();
	}

	@Override
	public NbtCompound getNbt() {
		return null;
	}

	@Override
	public Object getKey() {
		return this.block;
	}

	@Override
	public Identifier getId() {
		return Registries.BLOCK.getId(this.block);
	}

	@Override
	public List<Text> getTooltipText() {
		return List.of(this.getName());
	}

	@Override
	public List<TooltipComponent> getTooltip() {
		List<TooltipComponent> list = Lists.newArrayList();

		if (!this.isEmpty()) {
			// Name
			list.add(TooltipComponent.of(this.getName().asOrderedText()));
			// Technical name
			if (CLIENT.options.advancedItemTooltips) {
				Text technicalName = Text.literal(this.getId().toString()).formatted(Formatting.DARK_GRAY);

				list.add(TooltipComponent.of(technicalName.asOrderedText()));
			}

			if (this.describeSingleState != null) {
				List<Text> propertyTexts = createPropertyTexts(this.describeSingleState);

				for (Text text : propertyTexts) {
					list.add(TooltipComponent.of(text.asOrderedText()));
				}
			} else {
				// Add info for each state
				for (Map.Entry<BlockPos, BlockState> part : this.states.entrySet()) {
					BlockPos pos = part.getKey();
					BlockState state = part.getValue();

					List<Text> propertyTexts = createPropertyTexts(state);

					if (!propertyTexts.isEmpty()) {
						list.add(TooltipComponent.of(Text.empty().asOrderedText()));

						// Position text
						list.add(TooltipComponent.of(Text.literal(pos.toShortString() + ":")
							.formatted(Formatting.DARK_GRAY)
							.asOrderedText()));

						for (Text propertyText : propertyTexts) {
							list.add(TooltipComponent.of(propertyText.asOrderedText()));
						}
					}
				}
			}

			list.addAll(super.getTooltip());
		}

		return list;
	}

	protected List<Text> createPropertyTexts(@NotNull BlockState state) {
		List<Text> propertyTexts = Lists.newArrayList();

		for (Map.Entry<Property<?>, Comparable<?>> entry : state.getEntries().entrySet()) {
			Property<?> property = entry.getKey();
			Comparable<?> value = entry.getValue();

			if (!this.hiddenProperties.contains(property)) {
				MutableText keyText = Text.literal(property.getName() + ": ").formatted(Formatting.GRAY);
				MutableText valueText = Text.literal(Util.getValueAsString(property, value));

				MutableText combined;

				if (Boolean.TRUE.equals(value)) {
					combined = keyText.append(valueText.formatted(Formatting.GREEN));
				} else if (Boolean.FALSE.equals(value)) {
					combined = keyText.append(valueText.formatted(Formatting.RED));
				} else {
					combined = keyText.append(valueText);
				}

				propertyTexts.add(combined);
			}
		}

		return propertyTexts;
	}

	@Override
	public Text getName() {
		return this.block.getName();
	}
}
