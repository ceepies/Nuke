package duke.modules.impl.visual;

import duke.Duke;
import duke.modules.Module;
import duke.modules.ModuleManager;
import duke.modules.events.impl.Event;
import duke.modules.events.impl.EventRender2D;
import duke.modules.settings.impl.BooleanSetting;
import duke.modules.settings.impl.ModeSetting;
import duke.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Comparator;

public class HUD extends Module {
    public ModeSetting hudMode = new ModeSetting("Theme", "Duke", "Duke", "Full");
    public BooleanSetting left = new BooleanSetting("Left", true);
    public HUD(){
        super("HUD", Keyboard.KEY_NONE, Category.VISUAL);
        this.addSettings(hudMode, left);;
    }
    Timer timer = new Timer();

    public void onEvent(Event event){
        if(event instanceof EventRender2D){
            int color = -1;
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            Duke.moduleManager.getModules2().sort(Comparator.comparingDouble(m -> (double) mc.fontRendererObj.getStringWidth(((Module)m).getDisplayName())).reversed());
            switch (hudMode.name()){
                case "Duke": {
                    int count = 0;
                    for(Module m : Duke.moduleManager.getModules()) {

                        m.setX(animate(m.getX(), !left.isEnabled() ? (m.isEnabled() ? sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) : sr.getScaledWidth() + 25) : (m.isEnabled() ? 2 : -mc.fontRendererObj.getStringWidth(m.getDisplayName())), 0.04f));
                        m.setY(this.animate(m.getY(), count, 0.04f));
                        mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(),  m.getX(), m.getY() + 2, color);
                        if (m.isEnabled()) count += 10;
                    }
                    break;
                }
                case "Full": {
                    int count = 0;
                    for(Module m : Duke.moduleManager.getModules()) {
                        if(!left.isEnabled()) {
                            m.setX(animate(m.getX(), !left.isEnabled() ? (m.isEnabled() ? sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) : sr.getScaledWidth() + 15) : (m.isEnabled() ? 2 : -mc.fontRendererObj.getStringWidth(m.getDisplayName())), 0.04f));
                            m.setY(this.animate(m.getY(), count, 0.04f));
                            Gui.drawRect(m.getX() - 3, m.getY(), sr.getScaledWidth(), m.getY() + 11, color);
                            Gui.drawRect(m.getX() - 2, m.getY(), sr.getScaledWidth(), m.getY() + 10, new Color(30, 30, 30, 255).getRGB());
                            mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), m.getX() - .5f, m.getY() + 1.5f, color);
                            if (m.isEnabled()) count += 10;
                        }
                    }
                    break;
                }
            }
        }
    }
    public static float animate(float current, float end, float minSpeed) {
        float movement = (end - current) * 0.105f;
        if (movement > 0) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }
        return current + movement;
    }
}
