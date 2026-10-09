package dev.snooze.module;

import dev.snooze.module.modules.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public final List<Module> all = new ArrayList<>();

    public ModuleManager() {
        // Neue Module einfach hier registrieren
        all.add(new Sprint());
        all.add(new Fly());
        all.add(new Speed());
        all.add(new Step());
        all.add(new Fullbright());
        all.add(new TriggerBot());
    }

    public void tick() { for (Module m : all) if (m.enabled) m.onTick(); }

    public List<Module> in(Module.Category c) {
        List<Module> l = new ArrayList<>();
        for (Module m : all) if (m.category == c) l.add(m);
        return l;
    }
}
