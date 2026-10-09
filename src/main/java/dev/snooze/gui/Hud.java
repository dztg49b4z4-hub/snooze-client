package dev.snooze.gui;

import dev.snooze.SnoozeClient;
import dev.snooze.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.Comparator;
import java.util.List;

public class Hud {
    public static void render(DrawContext c) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden || SnoozeClient.modules == null) return;
        var tr = mc.textRenderer;

        // Watermark
        c.fill(4, 4, 8 + tr.getWidth("Snooze Client") + 22, 20, 0xB00B0D17);
        Theme.can(c, 14, 12, 5, Theme.CANS[0][0], Theme.CANS[0][1]);
        c.drawTextWithShadow(tr, "Snooze Client", 24, 8, Theme.ACCENT);

        // Arraylist rechts oben
        List<Module> on = SnoozeClient.modules.all.stream().filter(m -> m.enabled)
                .sorted(Comparator.comparingInt((Module m) -> -tr.getWidth(m.name))).toList();
        int w = c.getScaledWindowWidth(), y = 4;
        for (Module m : on) {
            int tw = tr.getWidth(m.name);
            c.fill(w - tw - 10, y, w, y + 11, 0xA00B0D17);
            c.fill(w - 2, y, w, y + 11, Theme.ACCENT);
            c.drawTextWithShadow(tr, m.name, w - tw - 6, y + 2, Theme.TEXT);
            y += 11;
        }
    }
}
