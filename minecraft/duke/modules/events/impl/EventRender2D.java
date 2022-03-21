package duke.modules.events.impl;

import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event<EventRender2D> {
    private final ScaledResolution resolution;
    private final float partialTicks;

    public EventRender2D(ScaledResolution resolution, float partialTicks) {
        this.resolution = resolution;
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public ScaledResolution getResolution() {
        return this.resolution;
    }
}
