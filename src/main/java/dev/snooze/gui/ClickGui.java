package dev.snooze.gui;

import dev.snooze.SnoozeClient;
import dev.snooze.module.Module;
import dev.snooze.module.Setting;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.HashSet;
import java.util.Set;

public class ClickGui extends Screen {
    private static final int PW = 120, HH = 20, RH = 16, SH = 14;
    private static final int[] px = new int[Module.Category.values().length];
    private static final int[] py = new int[Module.Category.values().length];
    private static boolean init;

    private final Set<Module> expanded = new HashSet<>();
    private int dragPanel = -1, dragOffX, dragOffY;
    private Setting dragSetting; private int dragSx;

    public ClickGui() {
        super(Text.literal("Snooze Client"));
        if (!init) { for (int i = 0; i < px.length; i++) { px[i] = 20 + i * (PW + 10); py[i] = 30; } init = true; }
    }

    @Override public boolean shouldPause() { return false; }

    @Override
    public void render(DrawContext c, int mx, int my, float delta) {
        Theme.background(c, width, height);

        // Titel mit Dosen
        c.drawCenteredTextWithShadow(textRenderer, "SNOOZE CLIENT", width / 2, 8, Theme.ACCENT);
        for (int i = 0; i < Theme.CANS.length; i++)
            Theme.can(c, width / 2 - 70 + i * 70, 8 + 18, 7, Theme.CANS[i][0], Theme.CANS[i][1]);

        // Slider live updaten
        if (dragSetting != null) dragSetting.set(dragSetting.min +
                (dragSetting.max - dragSetting.min) * Math.max(0, Math.min(1, (mx - dragSx) / (double) (PW - 8))));

        Module.Category[] cats = Module.Category.values();
        for (int i = 0; i < cats.length; i++) {
            int x = px[i], y = py[i];
            c.fill(x, y, x + PW, y + HH, Theme.HEADER);
            c.fill(x, y + HH - 2, x + PW, y + HH, Theme.ACCENT);
            Theme.can(c, x + 10, y + 10, 5, Theme.CANS[i % 3][0], Theme.CANS[i % 3][1]);
            c.drawTextWithShadow(textRenderer, cats[i].name(), x + 20, y + 6, Theme.TEXT);

            int cy = y + HH;
            for (Module m : SnoozeClient.modules.in(cats[i])) {
                boolean hover = mx >= x && mx < x + PW && my >= cy && my < cy + RH;
                c.fill(x, cy, x + PW, cy + RH, hover ? Theme.ROW_HOVER : Theme.ROW);
                if (m.enabled) c.fill(x, cy, x + 2, cy + RH, Theme.ACCENT);
                c.drawTextWithShadow(textRenderer, m.name, x + 7, cy + 4, m.enabled ? Theme.ACCENT : Theme.TEXT);
                if (!m.settings.isEmpty())
                    c.drawTextWithShadow(textRenderer, expanded.contains(m) ? "-" : "+", x + PW - 10, cy + 4, Theme.MUTED);
                cy += RH;
                if (expanded.contains(m)) {
                    for (Setting s : m.settings) {
                        c.fill(x, cy, x + PW, cy + SH, 0xF00F1220);
                        if (s.isBool) {
                            c.drawTextWithShadow(textRenderer, s.name, x + 8, cy + 3, Theme.MUTED);
                            c.drawTextWithShadow(textRenderer, s.on() ? "ON" : "OFF", x + PW - 24, cy + 3,
                                    s.on() ? Theme.ACCENT : Theme.MUTED);
                        } else {
                            double f = (s.get() - s.min) / (s.max - s.min);
                            c.fill(x + 4, cy + SH - 3, x + 4 + (int) ((PW - 8) * f), cy + SH - 1, Theme.ACCENT2);
                            c.drawTextWithShadow(textRenderer, s.name + ": " + String.format("%.2f", s.get()),
                                    x + 8, cy + 2, Theme.MUTED);
                        }
                        cy += SH;
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mx = click.x(), my = click.y(); int btn = click.button();
        Module.Category[] cats = Module.Category.values();
        for (int i = 0; i < cats.length; i++) {
            int x = px[i], y = py[i];
            if (mx >= x && mx < x + PW && my >= y && my < y + HH) {
                if (btn == 0) { dragPanel = i; dragOffX = (int) mx - x; dragOffY = (int) my - y; }
                return true;
            }
            int cy = y + HH;
            for (Module m : SnoozeClient.modules.in(cats[i])) {
                if (mx >= x && mx < x + PW && my >= cy && my < cy + RH) {
                    if (btn == 0) m.toggle();
                    else if (btn == 1 && !m.settings.isEmpty()) { if (!expanded.remove(m)) expanded.add(m); }
                    return true;
                }
                cy += RH;
                if (expanded.contains(m)) for (Setting s : m.settings) {
                    if (mx >= x && mx < x + PW && my >= cy && my < cy + SH && btn == 0) {
                        if (s.isBool) s.toggle(); else { dragSetting = s; dragSx = x + 4; }
                        return true;
                    }
                    cy += SH;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double dx, double dy) {
        if (dragPanel >= 0) { px[dragPanel] = (int) click.x() - dragOffX; py[dragPanel] = (int) click.y() - dragOffY; return true; }
        return super.mouseDragged(click, dx, dy);
    }

    @Override
    public boolean mouseReleased(Click click) { dragPanel = -1; dragSetting = null; return super.mouseReleased(click); }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) { close(); return true; }
        return super.keyPressed(input);
    }
}
