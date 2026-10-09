package dev.snooze.module.modules;

import dev.snooze.module.*;

public class Fly extends Module {
    private final Setting speed = add(Setting.num("Speed", 1.0, 0.1, 5.0));
    public Fly() { super("Fly", "Fliegen im Survival.", Category.MOVEMENT); }
    @Override public void onTick() {
        var p = mc.player;
        double s = speed.get();
        double yaw = Math.toRadians(p.getYaw());
        double x = 0, z = 0;
        if (mc.options.forwardKey.isPressed()) { x -= Math.sin(yaw); z += Math.cos(yaw); }
        if (mc.options.backKey.isPressed())    { x += Math.sin(yaw); z -= Math.cos(yaw); }
        if (mc.options.leftKey.isPressed())    { x += Math.cos(yaw); z += Math.sin(yaw); }
        if (mc.options.rightKey.isPressed())   { x -= Math.cos(yaw); z -= Math.sin(yaw); }
        double y = 0;
        if (mc.options.jumpKey.isPressed()) y += s * 0.5;
        if (mc.options.sneakKey.isPressed()) y -= s * 0.5;
        p.setVelocity(x * s * 0.5, y, z * s * 0.5);
    }
}
