// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.rendering.animation;

import net.minecraft.util.math.MathHelper;

public class WingAnimator {
	private float prevSpeed;
	private float speed;
	private float pos;

	public void updateWings(float speed) {
		this.prevSpeed = this.speed;
		this.speed = this.speed + (speed - this.speed) * 0.4f;

		this.pos += this.speed;
	}

	public float getSpeed(float tickDelta) {
		return MathHelper.lerp(tickDelta, this.prevSpeed, this.speed);
	}

	public float getPos(float tickDelta) {
		return this.pos - this.speed * (1f - tickDelta);
	}
}
