package duke.modules.impl.movement;

import duke.modules.Module;
import duke.modules.events.impl.Event;
import duke.modules.events.impl.EventUpdate;
import duke.modules.settings.impl.ModeSetting;
import duke.modules.settings.impl.NumberSetting;
import duke.util.MovementUtil;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {
    public float speed;
    public ModeSetting mode = new ModeSetting("Fly Mode", "Vanilla", "Vanilla", "Verus-Old");
    public NumberSetting speedSetting = new NumberSetting("Vanilla Speed", 1, 0.3, 4, 0.05);
    public NumberSetting yawSetting = new NumberSetting("Camera Yaw", 1, 0, 100, 1);
    public Flight() {
        super("Flight", Keyboard.KEY_F, Category.MOVEMENT);
        this.addSettings(mode,speedSetting,yawSetting);
    }
    public void onEnable(){
        speed = 0.275f;
        mc.thePlayer.motionY = 0.5;
    }
    public void onDisable(){
        MovementUtil.setSpeed(0);
        mc.timer.timerSpeed = 1;
    }
    public void onEvent(Event e){
        //rift if you are making smth with settings, use switch not .is" cause switch is more optimized
        switch(mode.name()){
            case "Vanilla":{
                if(e instanceof EventUpdate) {
                    mc.thePlayer.cameraYaw = (float) (yawSetting.getValue() / 100);
                    mc.thePlayer.motionY = mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : mc.gameSettings.keyBindJump.isKeyDown() ?1 : 0;
                    MovementUtil.setSpeed(speedSetting.getValue());
                }
                break;
            }
            case "Verus-Old":{
                if(e instanceof EventUpdate) {
                    if(mc.thePlayer.ticksExisted % 10 == 0){
                        speed = 0.275f;
                        mc.thePlayer.motionY = 0.2;
                    }
                    if(mc.thePlayer.ticksExisted % 20 == 0){
                        speed = 1f;
                        mc.timer.timerSpeed = 1;
                        mc.thePlayer.motionY -=0.9;
                    }
                    MovementUtil.setSpeed(speed);
                    if(mc.thePlayer.motionY < 0) {
                        mc.thePlayer.cameraYaw = .2f;
                        if(!(mc.thePlayer.ticksExisted % 40 == 0)) {
                            mc.thePlayer.motionY = 0;
                        }
                        mc.thePlayer.onGround = true;
                    }
                }
                break;
            }
        }
    }
}
