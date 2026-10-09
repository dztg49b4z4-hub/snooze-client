package dev.snooze.module.modules;

import dev.snooze.module.*;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
    private final Setting mult = add(Setting.num("Multiplier", 1.4, 1.0, 3.0));
    public Speed() { super("Speed", "Schneller laufen.", Category.MOVEMENT); }
    @Override public void onTick() {
        var p = mc.player;
        boolean moving = mc.options.forwardKey.isPressed() || mc.options.backKey.isPressed()
                || mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed();
        if (!moving || !p.isOnGround()) return;
        Vec3d v = p.getVelocity();
        p.setVelocity(v.x * mult.get(), v.y, v.z * mult.get());
    }
}
