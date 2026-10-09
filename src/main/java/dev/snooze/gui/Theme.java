package dev.snooze.gui;

import net.minecraft.client.gui.DrawContext;

public final class Theme {
    public static final int BG_TOP = 0xE00B0D17, BG_BOTTOM = 0xE0171A2B;
    public static final int PANEL = 0xF0141726, HEADER = 0xFF1E2238, ROW = 0xF01A1E30, ROW_HOVER = 0xF0262B45;
    public static final int ACCENT = 0xFF3DE0C5, ACCENT2 = 0xFF4C7DFF, TEXT = 0xFFE8EAF5, MUTED = 0xFF8A90AD;

    // Generische Dosen-Farben (Deckel, Koerper) - frei anpassbar
    public static final int[][] CANS = {
        {0xFF2FD3C0, 0xFFF2F4F8},   // "Pablo"
        {0xFF3B6BFF, 0xFF0F1630},   // "Velo"
        {0xFFC9A24B, 0xFF0A0A0A},   // "Cuba Black"
    };
    public static final String[] CAN_NAMES = { "Pablo", "Velo", "Cuba Black" };

    /** Runde Snus/Pouch-Dose von oben. */
    public static void can(DrawContext c, int cx, int cy, int r, int lid, int body) {
        disc(c, cx, cy, r, body);
        disc(c, cx, cy, r - Math.max(2, r / 8), lid);
        disc(c, cx, cy, r / 2, body);
        // kleiner Pouch in der Mitte
        int w = Math.max(3, r / 3);
        c.fill(cx - w, cy - w / 2, cx + w, cy + w / 2, 0xFFFFFFFF);
    }

    public static void disc(DrawContext c, int cx, int cy, int r, int color) {
        for (int dy = -r; dy <= r; dy++) {
            int w = (int) Math.sqrt((double) r * r - (double) dy * dy);
            c.fill(cx - w, cy + dy, cx + w, cy + dy + 1, color);
        }
    }

    /** Dezentes Dosen-Muster als Hintergrund. */
    public static void background(DrawContext c, int w, int h) {
        c.fillGradient(0, 0, w, h, BG_TOP, BG_BOTTOM);
        for (int gx = 0, i = 0; gx < w + 80; gx += 110) {
            for (int gy = 0; gy < h + 80; gy += 110, i++) {
                int ox = ((gy / 110) % 2) * 55;
                int[] col = CANS[i % CANS.length];
                int faded = 0x40; // Alpha
                can(c, gx + ox, gy, 22,
                    (faded << 24) | (col[0] & 0xFFFFFF), (faded << 24) | (col[1] & 0xFFFFFF));
            }
        }
    }
}
