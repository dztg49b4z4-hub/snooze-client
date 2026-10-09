package dev.snooze.module.modules;

import dev.snooze.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    public TriggerBot() { super("TriggerBot", "Schlaegt Ziel im Fadenkreuz.", Category.COMBAT); }
    @Override public void onTick() {
        if (mc.crosshairTarget instanceof EntityHitResult hit
                && hit.getEntity() instanceof LivingEntity
                && mc.player.getAttackCooldownProgress(0f) >= 1f) {
            mc.interactionManager.attackEntity(mc.player, hit.getEntity());
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
