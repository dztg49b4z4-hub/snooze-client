package dev.snooze.module.modules;

import dev.snooze.module.*;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module {
    private final Setting height = add(Setting.num("Height", 2.0, 0.6, 5.0));
    public Step() { super("Step", "Stufen hochlaufen.", Category.MOVEMENT); }
    @Override public void onTick() {
        var a = mc.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (a != null) a.setBaseValue(height.get());
    }
    @Override public void onDisable() {
        if (mc.player == null) return;
        var a = mc.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (a != null) a.setBaseValue(0.6);
    }
}
