package duke.modules;

import net.minecraft.client.Minecraft;
import duke.modules.events.impl.Event;
import duke.modules.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {
    public float x;
    public boolean inProcess;
    public String displayName;
    public float y;
    public String name;
    public float mSize;
    public float lastSize;
    public boolean toggled;
    public int keyCode;
    public Category category;


    public static Minecraft mc = Minecraft.getMinecraft();
    public List<Setting> settings = new ArrayList<Setting>();
    public Module(String name, int key, Category c){
        this.name = name;
        this.keyCode = key;
        this.category = c;
    }

    public void setY(float y){
        this.y = y;
    }
    public void setX(float x){
        this.x = x;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void onEvent(Event e){

    }

    public boolean isEnabled(){
        return toggled;
    }
    public int getKey(){
        return keyCode;
    }
    public void toggle() {
        toggled = !toggled;
        if(toggled)onEnable();else onDisable();
    }

    public void onEnable() {

    }
    public void onDisable() {

    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName != null ? displayName : name;
    }
    public void addSettings(Setting... settings){
        this.settings.addAll(Arrays.asList(settings));
    }
    public List<Setting> getSettings(){
        return settings;
    }
    public enum Category {
        COMBAT,
        MOVEMENT,
        PLAYER,
        VISUAL;
    }
}
