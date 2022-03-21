package duke.modules.impl.player;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import duke.modules.Module;
import duke.modules.events.impl.Event;
import duke.modules.events.impl.EventSendPacket;
import duke.modules.events.impl.EventUpdate;
import duke.modules.settings.impl.ModeSetting;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;
import java.util.SplittableRandom;

public class Disabler extends Module {
    public float speed;
    private static final LinkedList<C0FPacketConfirmTransaction> bypassList = new LinkedList<>();
    private static final SplittableRandom random = new SplittableRandom();
    private int current;
    public ModeSetting mode = new ModeSetting("Disabler Mode", "Verus airlines", "Verus airlines");
    public Disabler() {
        super("Disabler", Keyboard.KEY_NONE, Category.PLAYER);
        this.addSettings(mode);
    }
    public void onEnable(){
    }
    public void onDisable(){
    }
    public void onEvent(Event e){
        //rift if you are making smth with settings, use switch not .is" cause switch is more optimized
        switch(mode.name()){
            case "Verus airlines":{
                if(e instanceof EventUpdate) {
                    if(mc.thePlayer.ticksExisted < 5 && mc.theWorld != null) {
                        bypassList.clear();
                    }
                    if(mc.thePlayer.ticksExisted % 36 == 0 && bypassList.size() > 5) {
                        mc.thePlayer.sendQueue.sendQueueBypass(bypassList.poll());
                    }

                    if(mc.thePlayer.ticksExisted % 390 == 0) {
                        bypassList.clear();
                    }
                    mc.thePlayer.sendQueue.sendQueueBypass(new C00PacketKeepAlive(-1));
                    if(mc.thePlayer.ticksExisted % 55 == 0) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY -1000, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                    }
                }
                if(e instanceof EventSendPacket){
                    EventSendPacket sendPacket = (EventSendPacket) e;
                    if(sendPacket.getPacket() instanceof C0FPacketConfirmTransaction) {
                        bypassList.add((C0FPacketConfirmTransaction) ((EventSendPacket) e).getPacket());
                        e.setCancelled(true);
                    }
                    if(sendPacket.getPacket() instanceof C03PacketPlayer) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput());
                    }
                }
                break;
            }
        }
    }
}
