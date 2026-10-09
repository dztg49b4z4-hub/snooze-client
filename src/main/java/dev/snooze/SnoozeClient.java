package dev.snooze;

import dev.snooze.gui.ClickGui;
import dev.snooze.gui.Hud;
import dev.snooze.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class SnoozeClient implements ClientModInitializer {
    public static final String NAME = "Snooze Client";
    public static ModuleManager modules;
    private static KeyBinding openGui;

    @Override
    public void onInitializeClient() {
        modules = new ModuleManager();

        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.snooze.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.create(Identifier.of("snooze", "main"))));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openGui.wasPressed()) mc.setScreen(new ClickGui());
            if (mc.player != null) modules.tick();
        });

        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT,
                Identifier.of("snooze", "hud"), (ctx, tick) -> Hud.render(ctx));
    }
}
