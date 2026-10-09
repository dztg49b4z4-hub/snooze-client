package dev.snooze.module;

import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    public enum Category { COMBAT, MOVEMENT, RENDER, MISC }

    public final String name, description;
    public final Category category;
    public final List<Setting> settings = new ArrayList<>();
    public boolean enabled;
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    protected Module(String name, String description, Category category) {
        this.name = name; this.description = description; this.category = category;
    }

    protected <T extends Setting> T add(T s) { settings.add(s); return s; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}
