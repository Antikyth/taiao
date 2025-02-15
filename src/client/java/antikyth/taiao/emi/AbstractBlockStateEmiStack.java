// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public abstract class AbstractBlockStateEmiStack extends EmiStack {
	protected final MinecraftClient client = MinecraftClient.getInstance();
	protected final BlockRenderManager blockRenderManager = client.getBlockRenderManager();

	protected boolean offsetRotation = false;

	protected List<Property<?>> shownProperties = List.of();

	/**
	 * The default block item GUI transformation.
	 */
	protected static final Transformation TRANSFORMATION = new Transformation(
		new Vector3f(30f, 225f, 0f),
		new Vector3f(),
		new Vector3f(0.625f)
	);
	/**
	 * The default block item GUI transformation with a slight rotational offset.
	 */
	protected static final Transformation TRANSFORMATION_OFFSET = new Transformation(
		new Vector3f(30f, 210f, 0f),
		new Vector3f(),
		new Vector3f(0.625f)
	);

	protected List<Text> createPropertyTexts(@NotNull BlockState state) {
		List<Text> propertyTexts = Lists.newArrayList();

		for (Map.Entry<Property<?>, Comparable<?>> entry : state.getEntries().entrySet()) {
			Property<?> property = entry.getKey();
			Comparable<?> value = entry.getValue();

			if (this.shownProperties.contains(property)) {
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
}
