package dev.snooze.module.modules;

import dev.snooze.module.Module;

public class Sprint extends Module {
    public Sprint() { super("Sprint", "Rennt automatisch.", Category.MOVEMENT); }
    @Override public void onTick() {
        if (mc.options.forwardKey.isPressed() && !mc.player.isSneaking()) mc.player.setSprinting(true);
    }
}
