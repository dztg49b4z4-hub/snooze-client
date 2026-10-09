package dev.snooze.module.modules;

import dev.snooze.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Fullbright extends Module {
    public Fullbright() { super("Fullbright", "Immer hell.", Category.RENDER); }
    @Override public void onTick() {
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
    }
    @Override public void onDisable() {
        if (mc.player != null) mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
