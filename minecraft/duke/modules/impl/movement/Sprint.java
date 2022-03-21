package duke.modules.impl.movement;

import duke.modules.Module;
import duke.modules.events.impl.Event;
import duke.modules.events.impl.EventUpdate;
import duke.util.MovementUtil;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    public void onEvent(Event e){

        if(e instanceof EventUpdate){
            mc.thePlayer.setSprinting(mc.gameSettings.keyBindForward.isKeyDown() && MovementUtil.getSpeed() > 0 && !mc.gameSettings.keyBindBack.isKeyDown());

        }
    }
}
