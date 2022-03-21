package duke.modules.impl.visual;

import duke.clickgui.component.ClickGui;
import duke.modules.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public float speed;
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.VISUAL);
    }
    public void onEnable(){
        mc.displayGuiScreen(new ClickGui());
        this.toggle();
    }
}
