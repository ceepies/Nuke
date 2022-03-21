package duke.modules.impl.combat;


import java.util.LinkedList;
import java.util.Random;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.EnumChatFormatting;
import duke.modules.Module;
import duke.modules.events.impl.Event;
import duke.modules.events.impl.EventMotion;
import duke.modules.settings.Setting;
import duke.modules.settings.impl.BooleanSetting;
import duke.modules.settings.impl.NumberSetting;
import duke.util.Timer;

public class KillAura extends Module {
    public Random random = new Random();
    Setting currentOption;
    public Timer timer = new Timer();
    private static final LinkedList<Packet> PACKETS = new LinkedList<>();
    public static NumberSetting range = new NumberSetting("HitRange", 4.0D, 1.0D, 7.0D, 0.1D);
    public NumberSetting cps = new NumberSetting("Cps", 13.0D, 1.0D, 20.0D, 1.0D);
    public BooleanSetting Multi = new BooleanSetting("Multi", true);
    public BooleanSetting AutoBlock = new BooleanSetting("AutoBlock", true);
    public BooleanSetting players = new BooleanSetting("Players", true);
    public BooleanSetting animals = new BooleanSetting("Animals", true);
    public BooleanSetting mobs = new BooleanSetting("Mobs", true);

    public KillAura() {
        super("KillAura", 19, Category.COMBAT);
        this.addSettings(range,cps,Multi,AutoBlock, players,animals,mobs);

    }

    public void onEvent(Event e) {
        this.setDisplayName(this.name + " " + EnumChatFormatting.GRAY.toString() + "- " + Math.round(this.cps.getValue()));
        if(e instanceof EventMotion){
            if(Multi.isEnabled()) {
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityLivingBase) {
                        EntityLivingBase entity2 = (EntityLivingBase) entity;
                        if (mc.thePlayer.getDistanceToEntity(entity) <= range.getValue() && isValid(entity2)) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity2, Action.ATTACK));
                            mc.thePlayer.swingItem();
                            ((EventMotion) e).setYaw(getRotations(entity2)[0]);
                            ((EventMotion) e).setPitch(getRotations(entity2)[1]);
                        }
                    }
                }
            }else if(this.getTarget() != null){
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.getTarget(), Action.ATTACK));
                mc.thePlayer.swingItem();
                ((EventMotion) e).setYaw(getRotations(this.getTarget())[0]);
                ((EventMotion) e).setPitch(getRotations(this.getTarget())[1]);
            }
        }
    }

    public float[] getRotations(Entity e) {
        double deltaX = e.posX + e.posX - e.lastTickPosX - mc.thePlayer.posX;
        double deltaY = e.posY - 3.5D + (double)e.getEyeHeight() - mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight();
        double deltaZ = e.posZ + e.posZ - e.lastTickPosZ - mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2.0D) + Math.pow(deltaZ, 2.0D));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if(deltaX < 0.0D && deltaZ < 0.0D) {
            yaw = (float)(90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if(deltaX > 0.0D && deltaZ < 0.0D) {
            yaw = (float)(-90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[]{yaw, pitch};
    }
    public boolean isValid(Entity entity) {
        if(entity != null) {
            if(entity.getDistanceToEntity(mc.thePlayer) <= range.getValue()) {
                if(entity instanceof EntityVillager) {
                    return false;
                }
                if(entity instanceof EntityLivingBase) {
                    if(entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0) {
                        if(players.isEnabled() && (entity instanceof EntityOtherPlayerMP)) {
                            return true;
                        }
                        if((animals.isEnabled() ? entity instanceof EntityAnimal : false) || (mobs.isEnabled() ? entity instanceof EntityMob : false)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    public EntityLivingBase getTarget() {
        EntityLivingBase target = null;
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entity2 = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(entity) <= range.getValue() && isValid(entity2)) {
                    target = entity2;
                }
            }
        }
        return target;
    }
}
