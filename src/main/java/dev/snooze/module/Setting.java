package dev.snooze.module;

public class Setting {
    public final String name;
    public final boolean isBool;
    public double value, min, max;

    public static Setting bool(String n, boolean def) { Setting s = new Setting(n, true); s.value = def ? 1 : 0; return s; }
    public static Setting num(String n, double def, double min, double max) { Setting s = new Setting(n, false); s.value = def; s.min = min; s.max = max; return s; }
    private Setting(String n, boolean b) { name = n; isBool = b; }

    public boolean on() { return value > 0.5; }
    public double get() { return value; }
    public void toggle() { value = on() ? 0 : 1; }
    public void set(double v) { value = Math.max(min, Math.min(max, v)); }
}
