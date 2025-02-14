// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoAmbientOcclusionBakedModel implements BakedModel {
	protected final BakedModel inner;

	public NoAmbientOcclusionBakedModel(BakedModel inner) {
		this.inner = inner;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
		return this.inner.getQuads(state, face, random);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean hasDepth() {
		return true;
	}

	@Override
	public boolean isSideLit() {
		return true;
	}

	@Override
	public boolean isBuiltin() {
		return this.inner.isBuiltin();
	}

	@Override
	public Sprite getParticleSprite() {
		return this.inner.getParticleSprite();
	}

	@Override
	public ModelTransformation getTransformation() {
		return this.inner.getTransformation();
	}

	@Override
	public ModelOverrideList getOverrides() {
		return this.inner.getOverrides();
	}
}
